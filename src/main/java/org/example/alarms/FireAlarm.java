package org.example.alarms;

import java.util.Date;

public class FireAlarm extends Alarm
{
    public FireAlarm(Object source, Date date, String location, int importanceLevel) {
        super(source, date, location, importanceLevel, "Firefighter");
    }

    @Override
    public String getAlarmType() {
        return "Fire Alarm";
    }
}
