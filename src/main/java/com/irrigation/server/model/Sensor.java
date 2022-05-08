package com.irrigation.server.model;

public class Sensor {
    private int id;
    private boolean status;
    private int plotId;
    private Plot plot;

    public Sensor() {
    }

    public Sensor(int id, boolean status, int plotId) {
        this.id = id;
        this.status = status;
        this.plotId = plotId;
    }

    public Sensor(int id, boolean status, int plotId, Plot plot) {
        this(id, status, plotId);
        this.plot = plot;
    }

    public Sensor(int plotId) {
        this.plotId = plotId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Plot getPlot() {
        return plot;
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }
}
