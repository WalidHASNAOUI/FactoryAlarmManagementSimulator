package org.example.sensors;

import org.example.alarms.Alarm;

public abstract class Sensor
{
    protected String location;

    public Sensor(String location) {
        this.location = location;
    }

    public abstract Alarm generateAlarm(Object source, int importanceLevel);

    public String getLocation() {
        return location;
    }
}
