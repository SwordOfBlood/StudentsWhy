package com.example.studentswhy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Subject
{
    public Subject(String name, String teacher, String url, String status, String time)
    {
        this.name = name;
        this.status = status;
        this.teacher = teacher;
        this.time = time;
        this.url = url;
        id = UUID.randomUUID();
    }

    @SerializedName("id")
    @Expose
    private UUID id;
    public UUID getId()
    {
        return id;
    }

    @SerializedName("name")
    @Expose
    private String name;
    public String getName()
    {
        return name;
    }

    @SerializedName("teacher")
    @Expose
    private String teacher;
    public String getTeacher()
    {
        return teacher;
    }

    @SerializedName("url")
    @Expose
    private String url;
    public String getUrl()
    {
        return url;
    }

    @SerializedName("status")
    @Expose
    private String status;
    public String getStatus()
    {
        return status;
    }

    @SerializedName("time")
    @Expose
    private String time;
    public String getTime()
    {
        return time;
    }
}
