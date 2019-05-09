package com.example.studentswhy;

import java.util.List;
import java.util.UUID;

public class News
{
    public News(String hashTag, String title, String answer )
    {
        id = UUID.randomUUID();
        this.hashTag = hashTag;
        this.answer = answer;
        this.title = title;
    }

    private UUID id;
    public UUID getId()
    {
        return id;
    }

    private String hashTag;
    public String getHashTag()
    {
        return hashTag;
    }

    private String title;
    public String getTitle()
    {
        return title;
    }

    private String answer;
    public String getAnswer()
    {
        return answer;
    }
}
