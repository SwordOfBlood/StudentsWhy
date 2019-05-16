package com.example.studentswhy;

import java.util.List;
import java.util.UUID;

public class News
{
    public News(String date, String title, String answer, int likes, String hashtag)
    {
        id = UUID.randomUUID();
        this.date = date;
        this.answer = answer;
        this.title = title;
        this.likes = likes;
        this.hashtag = hashtag;
    }

    private String hashtag;
    public String getHashtag() {return hashtag;}

    private int likes;
    public int getLikes() {return likes;}
    public void setLikes(int likes) {this.likes = likes;}

    private boolean isLiked = false;
    public boolean getLiked(){return isLiked;}

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    private UUID id;
    public UUID getId()
    {
        return id;
    }

    private String date;
    public String getDate()
    {
        return date;
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
