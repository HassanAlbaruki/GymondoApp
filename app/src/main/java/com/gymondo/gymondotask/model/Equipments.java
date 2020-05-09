package com.gymondo.gymondotask.model;

//store Equipments object
public class Equipments {
    private int id;
    private String name;

    public Equipments(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Equipments() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
