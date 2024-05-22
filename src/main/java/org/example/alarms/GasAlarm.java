package org.example.alarms;

import java.util.Date;

public class GasAlarm extends Alarm
{
    private String gasType;

    public GasAlarm(Date date, String location, int importanceLevel, String gasType) {
        super(date, location, importanceLevel);
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
