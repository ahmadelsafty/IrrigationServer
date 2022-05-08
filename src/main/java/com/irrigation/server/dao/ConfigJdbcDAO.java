package com.irrigation.server.dao;

import com.irrigation.server.model.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigJdbcDAO implements ConfigDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlotDAO plotDAO;

    @Override
    public int update(Config config) {
        String updateStatement = "update config set water_meter_per_area_unit=?, "+
                "slot_time_in_minutes=?, sensor_irrigation_per_minute=?, "+
                "absorption_time_in_minutes=?, area_unit_per_sensor=?, retries=?";
        int updates = jdbcTemplate.update(updateStatement,
                new Object[] {
                        config.getWaterMeterPerAreaUnit(), config.getSlotTimeInMinutes(),
                        config.getSensorIrrigationPerMinute(), config.getAbsorptionTimeInMinutes(),
                        config.getAreaUnitPerSensor(), config.getRetries()});
        plotDAO.configUpdated();
        return updates;
    }

    @Override
    public Config get() {
        try {
            String selectStatement = "select * from config";
            Config config = jdbcTemplate.queryForObject(selectStatement,
                    BeanPropertyRowMapper.newInstance(Config.class));
            return config;
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
