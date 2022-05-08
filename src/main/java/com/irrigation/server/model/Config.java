package com.irrigation.server.model;

public class Config {
    private int absorptionTimeInMinutes;
    private int retries;
    private double sensorIrrigationPerMinute;
    private int areaUnitPerSensor;
    private int slotTimeInMinutes;
    private double waterMeterPerAreaUnit;

    public int getAreaUnitPerSensor() {
        return areaUnitPerSensor;
    }

    public void setAreaUnitPerSensor(int areaUnitPerSensor) {
        this.areaUnitPerSensor = areaUnitPerSensor;
    }

    public int getAbsorptionTimeInMinutes() {
        return absorptionTimeInMinutes;
    }

    public void setAbsorptionTimeInMinutes(int absorptionTimeInMinutes) {
        this.absorptionTimeInMinutes = absorptionTimeInMinutes;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public double getSensorIrrigationPerMinute() {
        return sensorIrrigationPerMinute;
    }

    public void setSensorIrrigationPerMinute(double sensorIrrigationPerMinute) {
        this.sensorIrrigationPerMinute = sensorIrrigationPerMinute;
    }

    public int getSlotTimeInMinutes() {
        return slotTimeInMinutes;
    }

    public void setSlotTimeInMinutes(int slotTimeInMinutes) {
        this.slotTimeInMinutes = slotTimeInMinutes;
    }

    public double getWaterMeterPerAreaUnit() {
        return waterMeterPerAreaUnit;
    }

    public void setWaterMeterPerAreaUnit(double waterMeterPerAreaUnit) {
        this.waterMeterPerAreaUnit = waterMeterPerAreaUnit;
    }
}
