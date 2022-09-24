package com.example.duacentes.models;

import java.io.Serializable;

public class PrincipleModel implements Serializable {
    private int idprinciple;
    private String name;
    private String description;
    private String image;
    private String creationdate;
    private String updatedate;
    private boolean state ;

    public PrincipleModel(int idprinciple, String name, String description, String image, String creationdate, String updatedate, boolean state) {
        this.idprinciple = idprinciple;
        this.name = name;
        this.description = description;
        this.image = image;
        this.creationdate = creationdate;
        this.updatedate = updatedate;
        this.state = state;
    }

    public int getIdprinciple() {
        return idprinciple;
    }

    public void setIdprinciple(int idprinciple) {
        this.idprinciple = idprinciple;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(String creationdate) {
        this.creationdate = creationdate;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}

