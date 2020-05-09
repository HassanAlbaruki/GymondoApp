package com.gymondo.gymondotask.model;

import java.util.List;

//handling the Exercises list response

public class ExerciseResponse {
    private int count;
    private String next;
    private String previous;
    private List<Exercises> results;

    public ExerciseResponse(int count, String next, String previous, List<Exercises> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public ExerciseResponse(){}

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

    public List<Exercises> getResults() {
        return results;
    }

    public void setResults(List<Exercises> results) {
        this.results = results;
    }
}
