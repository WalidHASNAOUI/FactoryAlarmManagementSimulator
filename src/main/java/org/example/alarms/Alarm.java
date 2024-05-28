package org.example.alarms;

import java.util.Date;
import java.util.EventObject;

public abstract class Alarm extends EventObject
{
    private Date date;
    private String location;
    private int importanceLevel;
    private String intendedService;

    public Alarm(Object source, Date date, String location, int importanceLevel, String intendedService) {
        super(source);
        this.date = date;
        this.location = location;
        this.importanceLevel = importanceLevel;
        this.intendedService = intendedService;
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

    public String getIntendedService() {
        return intendedService;
    }

    public abstract String getAlarmType();
}
