package org.example.sensors;

import org.example.alarms.Alarm;
import org.example.alarms.GasAlarm;

import java.util.Date;

public class GasSensor extends Sensor
{
    private String gasType;

    public GasSensor(String location, String gasType) {
        super(location);
        this.gasType = gasType;
    }

    @Override
    public Alarm generateAlarm() {
        return new GasAlarm(new Date(), getLocation(), 2, gasType); // Example importance level
    }
}
