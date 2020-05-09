package com.gymondo.gymondotask.model;

import java.util.List;

//handling the muscles list response

public class MusclesResponse {
    private int count;
    private String next;
    private String previous;
    private List<Muscles> results;

    public MusclesResponse(int count, String next, String previous, List<Muscles> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public MusclesResponse(){}

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

    public List<Muscles> getResults() {
        return results;
    }

    public void setResults(List<Muscles> results) {
        this.results = results;
    }
}
