package com.irrigation.server.dao;

import com.irrigation.server.model.Config;
import com.irrigation.server.model.Plot;
import com.irrigation.server.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PlotJdbcDAO implements PlotDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Plot createPlot(Plot plot) {
        String createStatement = "insert into plot(name, length, width) values(?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(createStatement, new String[]{"id"});
                        ps.setString(1, plot.getName());
                        ps.setDouble(2, plot.getLength());
                        ps.setDouble(3, plot.getWidth());
                        return ps;
                    }
                },
                keyHolder);

        int key = keyHolder.getKey().intValue();
        plot.setId(key);
        createPlotSensors(plot, getConfig());
        return plot;
    }

    @Override
    public List<Plot> getAllPlots() {
        List<Plot> allPlots = jdbcTemplate.query("select * from plot", BeanPropertyRowMapper.newInstance(Plot.class));
        for (Plot plot : allPlots) {
            List<Sensor> plotSensors = findPlotSensors(plot.getId());
            for (Sensor sensor : plotSensors) {
                plot.addSensor(sensor);
            }
        }
        return allPlots;
    }

    @Override
    public Plot findPlotById(int plotId) {
        try {
            Plot plot = jdbcTemplate.queryForObject("select * from plot where id=?",
                    BeanPropertyRowMapper.newInstance(Plot.class), plotId);
            List<Sensor> plotSensors = findPlotSensors(plot.getId());
            for (Sensor sensor : plotSensors) {
                plot.addSensor(sensor);
            }
            return plot;
        } catch (IncorrectResultSizeDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int updatePlot(Plot plot) {
        String updateStatement = "update plot set name=?, " +
                "length=?, width=? where id=?";
        int updates = jdbcTemplate.update(updateStatement,
                new Object[]{plot.getName(), plot.getLength(),
                        plot.getWidth(), plot.getId()});
        removeAllPlotSensors(plot.getId());
        Config config = getConfig();
        createPlotSensors(plot, config);
        return updates;
    }

    @Override
    public int deletePlot(int plotId) {
        removeAllPlotSensors(plotId);
        String deleteStatement = "delete from plot where id=?";
        int deletes = jdbcTemplate.update(deleteStatement, new Object[]{plotId});
        removeAllPlotSensors(plotId);
        return deletes;
    }

    @Override
    public List<Sensor> getAllSensors() {
        List<Sensor> sensorsList = jdbcTemplate.query("select * from sensor", BeanPropertyRowMapper.newInstance(Sensor.class));
        return sensorsList;
    }

    @Override
    public List<Sensor> findPlotSensors(int plotId) {
        List<Sensor> sensorsList = jdbcTemplate.query("select * from sensor where plot_id=?",
                BeanPropertyRowMapper.newInstance(Sensor.class), plotId);
        return sensorsList;
    }

    @Override
    public Sensor findSensorById(int sensorId) {
        try {
            Sensor sensor = jdbcTemplate.queryForObject("select * from sensor where id=?",
                    BeanPropertyRowMapper.newInstance(Sensor.class), sensorId);
            return sensor;
        } catch (IncorrectResultSizeDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int addSensorToPlot(int plotId) {
        String insertStatement = "insert into sensor(status, plot_id) values(?,?)";
        int inserts = jdbcTemplate.update(insertStatement, new Object[]{true, plotId});
        return inserts;
    }

    @Override
    public int removeSensor(int sensorId) {
        String deleteStatement = "delete from sensor where id=?";
        int deletes = jdbcTemplate.update(deleteStatement, new Object[]{sensorId});
        return deletes;
    }

    @Override
    public int removeAllSensors() {
        String deleteStatement = "delete from sensor";
        int deletes = jdbcTemplate.update(deleteStatement);
        return deletes;
    }

    @Override
    public int removeAllPlotSensors(int plotId) {
        String deleteStatement = "delete from sensor where plot_id=?";
        int deletes = jdbcTemplate.update(deleteStatement, new Object[]{plotId});
        return deletes;
    }

    @Override
    public int updateSensorStatus(int sensorId, boolean enabled) {
        String updateStatement = "update sensor set status=? where id=?";
        int updates = jdbcTemplate.update(updateStatement, new Object[]{enabled, sensorId});
        return updates;
    }

    @Override
    public void configUpdated() {
        Config config = getConfig();
        removeAllSensors();
        getAllPlots().stream().forEach(plot -> createPlotSensors(plot, config));
    }

    public void createPlotSensors(Plot plot, Config config) {
        int areaUnits = config.getAreaUnitPerSensor();
        double areaRounded = Math.ceil(plot.getArea());
        if (areaUnits > 0) {
            int sensorsCount = (int) Math.ceil(areaRounded / areaUnits);
            for (int i = 0; i < sensorsCount; i++) {
                addSensorToPlot(plot.getId());
            }
        }
    }

    private Config getConfig() {
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
