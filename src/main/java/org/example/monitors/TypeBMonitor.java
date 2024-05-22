package org.example.monitors;

import org.example.alarms.Alarm;
import org.example.alarms.GasAlarm;
import org.example.alarms.RadiationAlarm;

import java.util.ArrayList;
import java.util.List;

public class TypeBMonitor implements Monitor
{
    private List<Alarm> alarms = new ArrayList<>();

    @Override
    public void receiveAlarm(Alarm alarm) {
        if (alarm instanceof GasAlarm || alarm instanceof RadiationAlarm) {
            alarms.add(alarm);
            System.out.println("Received: " + alarm.getAlarmType() + " at " + alarm.getLocation());
        }
    }

    public List<Alarm> getAlarms() {
        return alarms;
    }
}
