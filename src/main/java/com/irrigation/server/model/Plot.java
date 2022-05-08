package com.irrigation.server.model;

import java.util.ArrayList;
import java.util.List;

public class Plot {
    private int id;
    private String name;
    private double length;
    private double width;
    private double area;
    private int sensorsCount;
    private List<Sensor> sensorsList;

    public Plot() {
    }

    public Plot(int id, String name, double length, double width, int sensorsCount) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.width = width;
        this.sensorsCount = sensorsCount;
    }

    public Plot(int id, String name, double length, double wdth, int sensorsCount, List<Sensor> sensorsList) {
        this(id,name,length,wdth,sensorsCount);
        this.sensorsList = sensorsList;
    }

    public void addSensor(Sensor sensor) {
        getSensorsList().add(sensor);
        sensorsCount = getSensorsList().size();
    }

    public void removeSensor(Sensor sensor) {
        getSensorsList().remove(sensor);
        sensorsCount = getSensorsList().size();
    }

    public List<Sensor> getSensorsList() {
        if (sensorsList == null)
            sensorsList = new ArrayList<>();
        return sensorsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
        setArea(length*width);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
        setArea(length*width);
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getSensorsCount() {
        return sensorsCount;
    }

    public void setSensorsCount(int sensorsCount) {
        this.sensorsCount = sensorsCount;
    }
}
