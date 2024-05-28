package org.example.alarms;

import java.util.Date;

public class GasAlarm extends Alarm
{
    private String gasType;

    public GasAlarm(Object source, Date date, String location, int importanceLevel, String gasType) {
        super(source, date, location, importanceLevel, "Environmental Service");
        this.gasType = gasType;
    }

    public String getGasType() {
        return gasType;
    }

    @Override
    public String getAlarmType() {
        return "Gas Alarm";
    }
}
