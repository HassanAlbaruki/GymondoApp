package com.gymondo.gymondotask.model;

import java.util.List;

//handling the categories list response
public class CategoryResponse {
    private int count;
    private String next;
    private String previous;
    private List<Category> results;

    public CategoryResponse(int count, String next, String previous, List<Category> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public CategoryResponse(){}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<Category> getResults() {
        return results;
    }

    public void setResults(List<Category> results) {
        this.results = results;
    }
}
