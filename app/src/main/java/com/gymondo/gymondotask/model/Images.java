package com.gymondo.gymondotask.model;

import java.io.Serializable;

public class Images implements Serializable {
    private int id;
    private String image;
    private int exercise;

    public Images(int id, String image, int exercise) {
        this.id = id;
        this.image = image;
        this.exercise = exercise;
    }
    public Images(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getExercise() {
        return exercise;
    }

    public void setExercise(int exercise) {
        this.exercise = exercise;
    }
}
