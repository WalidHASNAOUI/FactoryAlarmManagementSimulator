package org.example.sensors;

import org.example.alarms.Alarm;
import org.example.alarms.RadiationAlarm;

import java.util.Date;

public class RadiationSensor extends Sensor
{
    private int radiationLevel;

    public RadiationSensor(String location, int radiationLevel) {
        super(location);
        this.radiationLevel = radiationLevel;
    }

    @Override
    public Alarm generateAlarm() {
        return new RadiationAlarm(new Date(), getLocation(), 2, radiationLevel); // Example importance level
    }
}
