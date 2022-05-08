
System name: 
    IrrigationServer

Used Technologies:
    Springboot, SpringJDBC

Database: 
    H2 memory schema

Initialization SQL Statements (will be automatically executed at running the server):

    -- (DDL)
    create table config(water_meter_per_area_unit double, slot_time_in_minutes int, sensor_irrigation_per_minute double, absorption_time_in_minutes double, area_unit_per_sensor int, retries int);
    create table plot(id int auto_increment primary key, name varchar(50), length double, width double);
    create table sensor(id int auto_increment primary key, status boolean, plot_id int);
    -- (DML)
    insert into config(water_meter_per_area_unit, slot_time_in_minutes, sensor_irrigation_per_minute, absorption_time_in_minutes, area_unit_per_sensor, retries) values(0,0,0,0,0,0);
    insert into plot(name, length, width) values('Plot01', 10.5, 10.5);
    insert into plot(name, length, width) values('Plot02, 20.3, 10.0);
    insert into plot(name, length, width) values('Plot03', 15.25, 30.0)
    
then inserting another 10 row into plot table with random values