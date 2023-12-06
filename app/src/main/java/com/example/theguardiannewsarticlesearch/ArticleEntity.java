package com.example.theguardiannewsarticlesearch;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_articles")
public class ArticleEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String articleId;
    private String title;
    private String summary;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}