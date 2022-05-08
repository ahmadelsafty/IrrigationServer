package com.irrigation.server.controller;

import com.irrigation.server.dao.PlotDAO;
import com.irrigation.server.model.Plot;
import com.irrigation.server.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/plot")
public class PlotController {

    @Autowired
    private PlotDAO plotDao;

    @GetMapping("/getAllPlots")
    public List<Plot> getAllPlots() {
        return plotDao.getAllPlots();
    }

    @GetMapping("/findPlot/{id}")
    public Plot findPlotById(@PathVariable("id") int id) {
        return plotDao.findPlotById(id);
    }

    @PostMapping("/createPlot")
    public Plot createPlot(@RequestBody Plot plot) {
        return plotDao.createPlot(plot);
    }

    @PutMapping("/updatePlot")
    public int updatePlot(@RequestBody Plot plot) {
        return plotDao.updatePlot(plot);
    }

    @DeleteMapping("/deletePlot/{id}")
    public int deletePlot(@PathVariable("id") int id) {
        return plotDao.deletePlot(id);
    }

    @GetMapping("/getPlotSensors/{plotId}")
    public List<Sensor> getPlotSensors(@PathVariable("plotId") int plotId) {
        return plotDao.findPlotSensors(plotId);
    }

    @PutMapping("/updateSensorStatus/{sensorId}/{status}")
    public int updateSensorStatus(@PathVariable("sensorId") int sensorId, @PathVariable("status") boolean status) {
        return plotDao.updateSensorStatus(sensorId, status);
    }
}
