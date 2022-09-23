package ru.alexey.RESTful.project3REST.dto;

import javax.validation.constraints.*;

public class MeasurementDTO {
    @NotNull(message = "Значение не может быть пустым")
    @Min(value = -100, message = "Значение может быть от -100 до +100")
    @Max(value = 100, message = "Значение может быть от -100 до +100")
    private double value;
    @NotNull(message = "Значение не может быть пустым")
    private boolean raining;
    private SensorDTO sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
