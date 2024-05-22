package org.example.alarms;

import java.util.Date;

public abstract class Alarm
{
    private Date date;
    private String location;
    private int importanceLevel;

    public Alarm(Date date, String location, int importanceLevel) {
        this.date = date;
        this.location = location;
        this.importanceLevel = importanceLevel;
    }

    public Date getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public int getImportanceLevel() {
        return importanceLevel;
    }

    public abstract String getAlarmType();
}
