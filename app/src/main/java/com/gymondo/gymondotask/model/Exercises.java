package com.gymondo.gymondotask.model;

import java.io.Serializable;
import java.util.List;

//store Exercises object
public class Exercises implements Serializable {
    private int category;
    private String description;
    private List<Integer> equipment;
    private int id;
    private List<Integer> muscles;
    private List<Integer> musclesSecondary;
    private String name;
    private String status;

    public Exercises(int category, String description, List<Integer> equipment, int id, List<Integer> muscles, List<Integer> musclesSecondary, String name, String status) {
        this.category = category;
        this.description = description;
        this.equipment = equipment;
        this.id = id;
        this.muscles = muscles;
        this.musclesSecondary = musclesSecondary;
        this.name = name;
        this.status = status;
    }
    public Exercises(){}

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<Integer> equipment) {
        this.equipment = equipment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getMuscles() {
        return muscles;
    }

    public void setMuscles(List<Integer> muscles) {
        this.muscles = muscles;
    }

    public List<Integer> getMusclesSecondary() {
        return musclesSecondary;
    }

    public void setMusclesSecondary(List<Integer> musclesSecondary) {
        this.musclesSecondary = musclesSecondary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
