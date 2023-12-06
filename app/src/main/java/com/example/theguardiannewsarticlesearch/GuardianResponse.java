package com.example.theguardiannewsarticlesearch;

import java.util.List;

public class GuardianResponse {
    private String status;
    private String userTier;
    private int total;
    private int startIndex;
    private int pageSize;
    private int currentPage;
    private int pages;
    private String orderBy;
    private List<Article> results;

    // Getters
    public String getStatus() {
        return status;
    }

    public String getUserTier() {
        return userTier;
    }

    public int getTotal() {
        return total;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPages() {
        return pages;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public List<Article> getResults() {
        return results;
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserTier(String userTier) {
        this.userTier = userTier;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setResults(List<Article> results) {
        this.results = results;
    }
}