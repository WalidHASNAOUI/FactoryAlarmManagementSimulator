package org.example.alarms;

import java.util.Date;

public class FireAlarm extends Alarm
{
    public FireAlarm(Date date, String location, int importanceLevel) {
        super(date, location, importanceLevel);
    }

    @Override
    public String getAlarmType() {
        return "Fire Alarm";
    }
}
