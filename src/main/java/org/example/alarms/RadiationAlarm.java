package org.example.alarms;

import java.util.Date;

public class RadiationAlarm extends Alarm
{
    private int radiationLevel;

    public RadiationAlarm(Object source, Date date, String location, int importanceLevel, int radiationLevel) {
        super(source, date, location, importanceLevel, "Environmental Service");
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
