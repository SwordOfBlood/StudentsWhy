package com.example.studentswhy;

import java.util.UUID;

public class Teacher
{
    public Teacher(String url, String name, String place, String number, String email, String rank)
    {
        this.email = email;
        this.name = name;
        this.number = number;
        this.place = place;
        this.rank = rank;
        this.url = url;
        id = UUID.randomUUID();
    }

    private UUID id;
    public UUID getId()
    {
        return id;
    }

    private String url;
    public String getUrl()
    {
        return url;
    }

    private String name;
    public String getName()
    {
        return name;
    }

    private String rank;
    public String getRank()
    {
        return rank;
    }
    public void setRank(String rank){this.rank = rank;}

    private String place;
    public String getPlace()
    {
        return place;
    }
    public void setPlace(String place){this.place = place;}

    private String number;
    public String getNumber()
    {
        return number;
    }
    public void setNumber(String number){this.number = number;}

    private String email;
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email){this.email = email;}
}
