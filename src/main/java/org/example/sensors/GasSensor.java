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
    public GasAlarm generateAlarm(Object source, int importanceLevel) {
        return new GasAlarm(source, new Date(), location, importanceLevel, gasType);
    }

    public String getGasType() {
        return gasType;
    }
}
