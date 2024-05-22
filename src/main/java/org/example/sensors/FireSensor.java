package org.example.sensors;

import org.example.alarms.Alarm;
import org.example.alarms.FireAlarm;

import java.util.Date;

public class FireSensor extends Sensor
{
    public FireSensor(String location) {
        super(location);
    }

    @Override
    public Alarm generateAlarm() {
        return new FireAlarm(new Date(), getLocation(), 2); // Example importance level
    }
}
