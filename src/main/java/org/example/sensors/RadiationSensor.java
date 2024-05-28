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
    public RadiationAlarm generateAlarm(Object source, int importanceLevel) {
        return new RadiationAlarm(source, new Date(), location, importanceLevel, radiationLevel);
    }

    public int getRadiationLevel() {
        return radiationLevel;
    }
}
