package com.gymondo.gymondotask.model;

import java.util.List;

//handling the images list response

public class ImagesResponse {
    private int count;
    private String next;
    private String previous;
    private List<Images> results;

    public ImagesResponse(int count, String next, String previous, List<Images> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public ImagesResponse(){}

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

    public List<Images> getResults() {
        return results;
    }

    public void setResults(List<Images> results) {
        this.results = results;
    }
}
