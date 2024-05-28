package org.example.listeners;

import org.example.alarms.Alarm;

public interface AlarmEventListener
{
    void alarmGenerated(Alarm alarm);
}
