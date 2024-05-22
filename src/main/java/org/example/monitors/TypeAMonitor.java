package org.example.monitors;

import org.example.alarms.Alarm;
import org.example.alarms.FireAlarm;
import org.example.alarms.GasAlarm;

import java.util.ArrayList;
import java.util.List;

public class TypeAMonitor implements Monitor
{
    private List<Alarm> alarms = new ArrayList<>();

    @Override
    public void receiveAlarm(Alarm alarm) {
        if (alarm instanceof FireAlarm || alarm instanceof GasAlarm) {
            alarms.add(alarm);
            System.out.println("Received: " + alarm.getAlarmType() + " at " + alarm.getLocation());
        }
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }
}
