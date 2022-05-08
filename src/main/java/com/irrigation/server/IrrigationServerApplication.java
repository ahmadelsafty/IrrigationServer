package com.irrigation.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.text.DecimalFormat;

@SpringBootApplication
public class IrrigationServerApplication {
    @Value("${spring.datasource.driver-class-name}")
    private String dbDriver;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.sources(IrrigationServerApplication.class);
        builder.run(args);
    }
    @Bean
    ApplicationRunner applicationRunner(Environment env) {
        return args -> {
            DecimalFormat df = new DecimalFormat("0.00");
            String plotTableCreate = "create table plot(id int auto_increment "+
                    "primary key, name varchar(50), length double, width double)";
            String confTableCreate = "create table config("+
                    "water_meter_per_area_unit double, slot_time_in_minutes int, "+
                    "sensor_irrigation_per_minute double, absorption_time_in_minutes double, "+
                    "area_unit_per_sensor int, retries int)";
            String sensorTableCreate = "create table sensor(id int auto_increment primary key, "+
                    "status boolean, plot_id int)";
            String plotTableInsert = "insert into plot(name, length, width) values(?, ?, ?)";
            String confTableInsert = "insert into config("+
                    "water_meter_per_area_unit, slot_time_in_minutes, "+
                    "sensor_irrigation_per_minute, absorption_time_in_minutes, "+
                    "area_unit_per_sensor, retries) values(0,0,0,0,0,0)";
            String[] namesArray = {"Plot01", "Plot02", "Plot03"};
            double[] lengthsArray = {10.5, 20.3, 15.25};
            double[] widthsArray = {10.5, 10.0, 30.0};
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
            dataSource.setUrl(env.getProperty("spring.datasource.url"));
            dataSource.setUsername(env.getProperty("spring.datasource.username"));
            dataSource.setPassword(env.getProperty("spring.datasource.password"));

            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            // create plot table
            jdbcTemplate.update(plotTableCreate);
            // insert predefined 3 rows into plot table
            for (int i = 0; i < namesArray.length; i++) {
                jdbcTemplate.update(plotTableInsert,
                        new Object[] {namesArray[i], df.format(lengthsArray[i]), df.format(widthsArray[i])});
            }
            // insert 7 rows into plot table with random length and width
            for (int i = namesArray.length + 1; i <= 10; i++) {
                String name = String.format("Plot%02d", i);
                double length = (Math.random() * (i - 1)) + 1;
                double width = (Math.random() * (i - 1)) + 1;
                jdbcTemplate.update(plotTableInsert, new Object[] {name, df.format(length), df.format(width)});
            }

            // create config table
            jdbcTemplate.update(confTableCreate);
            // insert temp row into config table
            jdbcTemplate.update(confTableInsert);

            // create sensor table
            jdbcTemplate.update(sensorTableCreate);
        };
    }

}
