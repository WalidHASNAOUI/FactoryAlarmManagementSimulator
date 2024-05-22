package org.example.sensors;

import org.example.alarms.Alarm;

public abstract class Sensor
{
    private String location;

    public Sensor(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public abstract Alarm generateAlarm();
}
