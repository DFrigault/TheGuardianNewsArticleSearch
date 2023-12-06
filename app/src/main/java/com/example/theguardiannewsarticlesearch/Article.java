package com.example.theguardiannewsarticlesearch;

public class Article {
    private String webTitle;
    private String webUrl;
    private String sectionName;
    private boolean isFavorite;

    // Constructor
    public Article(String webTitle, String webUrl, String sectionName) {
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.sectionName = sectionName;
    }

    // Getters and setters
    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
