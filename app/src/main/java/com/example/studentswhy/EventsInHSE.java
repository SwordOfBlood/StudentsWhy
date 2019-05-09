package com.example.studentswhy;

import java.util.List;

public class EventsInHSE extends News
{
    public EventsInHSE(String hashTag, String title, String answer, String time, String place)
    {
        super(hashTag, title, answer);
        this.time = time;
        this.place = place;
    }

    private String time;
    public String getTime()
    {
        return time;
    }

    private String place;
    public String getPlace()
    {
        return place;
    }
}
