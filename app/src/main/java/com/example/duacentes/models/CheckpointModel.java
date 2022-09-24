package com.example.duacentes.models;

import java.io.Serializable;

public class CheckpointModel implements Serializable {
    private int idcheckpoint;
    private int idprinciple;
    private String principle;
    private int idguideline;
    private String guideline;
    private String name;
    private String description;
    private String image;
    private String creationdate;
    private String updatedate;
    private boolean state;

    public CheckpointModel(int xidcheckpoint, int idprinciple, String principle, int idguideline, String guideline, String name, String description, String image, String creationdate, String updatedate, boolean state) {
        this.idcheckpoint = xidcheckpoint;
        this.idprinciple = idprinciple;
        this.principle = principle;
        this.idguideline = idguideline;
        this.guideline = guideline;
        this.name = name;
        this.description = description;
        this.image = image;
        this.creationdate = creationdate;
        this.updatedate = updatedate;
        this.state = state;
    }

    public int getIdcheckpoint() {
        return idcheckpoint;
    }

    public void setIdcheckpoint(int idcheckpoint) {
        this.idcheckpoint = idcheckpoint;
    }

    public int getIdprinciple() {
        return idprinciple;
    }

    public void setIdprinciple(int idprinciple) {
        this.idprinciple = idprinciple;
    }

    public String getPrinciple() {
        return principle;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    public int getIdguideline() {
        return idguideline;
    }

    public void setIdguideline(int idguideline) {
        this.idguideline = idguideline;
    }

    public String getGuideline() {
        return guideline;
    }

    public void setGuideline(String guideline) {
        this.guideline = guideline;
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

