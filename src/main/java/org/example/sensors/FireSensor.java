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
    public FireAlarm generateAlarm(Object source, int importanceLevel) {
        return new FireAlarm(source, new Date(), location, importanceLevel);
    }
}
