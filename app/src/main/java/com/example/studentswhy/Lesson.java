package com.example.studentswhy;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Lesson
{
    public Lesson(String auditorium, String building, String dayOfWeekString,
                  String beginLesson, String date, String discipline,
                  String kindOfWork, String lecturer)
    {
        this.auditorium = auditorium;
        this.beginLesson = beginLesson;
        this.building = building;
        this.date = date;
        this.dayOfWeekString = dayOfWeekString;
        this.discipline = discipline;
        this.kindOfWork = kindOfWork;
        this.lecturer = lecturer;
        id = UUID.randomUUID();
    }

    @SerializedName("id")
    @Expose
    private UUID id;
    public UUID getId()
    {
        return id;
    }

    @SerializedName("auditorium")
    @Expose
    private String auditorium;
    public String getAuditorium()
    {
        return auditorium;
    }

    @SerializedName("building")
    @Expose
    private String building;
    public String getBuilding()
    {
        return building;
    }

    @SerializedName("dayOfWeekString")
    @Expose
    private String dayOfWeekString;
    public String getDayOfWeekString()
    {
        return dayOfWeekString;
    }

    @SerializedName("beginLesson")
    @Expose
    private String beginLesson;
    public String getBeginLesson()
    {
        return beginLesson;
    }

    @SerializedName("date")
    @Expose
    private String date;
    public String getDate()
    {
        return date;
    }

    @SerializedName("discipline")
    @Expose
    private String discipline;
    public String getDiscipline()
    {
        return discipline;
    }

    @SerializedName("kindOfWork")
    @Expose
    private String kindOfWork;
    public String getKindOfWork()
    {
        return kindOfWork;
    }

    @SerializedName("lecturer")
    @Expose
    private String lecturer;
    public String getLecturer()
    {
        return lecturer;
    }
}
