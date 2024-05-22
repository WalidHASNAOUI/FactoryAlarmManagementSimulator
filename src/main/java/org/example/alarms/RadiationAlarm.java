package org.example.alarms;

import java.util.Date;

public class RadiationAlarm extends Alarm
{
    private int radiationLevel;

    public RadiationAlarm(Date date, String location, int importanceLevel, int radiationLevel) {
        super(date, location, importanceLevel);
        this.radiationLevel = radiationLevel;
    }

    public int getRadiationLevel() {
        return radiationLevel;
    }

    @Override
    public String getAlarmType() {
        return "Radiation Alarm";
    }
}
