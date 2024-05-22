package org.example.monitors;

import org.example.alarms.Alarm;

public interface Monitor
{
    void receiveAlarm(Alarm alarm);
}
