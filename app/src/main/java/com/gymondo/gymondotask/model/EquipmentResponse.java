package com.gymondo.gymondotask.model;

import java.util.List;

//handling the equipments list response

public class EquipmentResponse {
    private int count;
    private String next;
    private String previous;
    private List<Equipments> results;

    public EquipmentResponse(int count, String next, String previous, List<Equipments> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public EquipmentResponse(){}

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

    public List<Equipments> getResults() {
        return results;
    }

    public void setResults(List<Equipments> results) {
        this.results = results;
    }
}
