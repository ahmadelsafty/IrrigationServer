package com.irrigation.server.dao;

import com.irrigation.server.model.Config;
import com.irrigation.server.model.Plot;
import com.irrigation.server.model.Sensor;

import java.util.List;

public interface PlotDAO {
    Plot createPlot(Plot plot);
    List<Plot> getAllPlots();
    Plot findPlotById(int plotId);
    int updatePlot(Plot plot);
    int deletePlot(int plotId);
    List<Sensor> getAllSensors();
    List<Sensor> findPlotSensors(int plotId);
    Sensor findSensorById(int sensorId);
    int addSensorToPlot(int plotId);
    int removeSensor(int sensorId);
    int removeAllSensors();
    int removeAllPlotSensors(int plotId);
    int updateSensorStatus(int sensorId, boolean enabled);
    void configUpdated();
}
