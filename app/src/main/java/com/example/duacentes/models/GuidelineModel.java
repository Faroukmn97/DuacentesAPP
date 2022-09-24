package com.example.duacentes.models;

import java.io.Serializable;

public class GuidelineModel implements Serializable {

    private int idguideline;
    private String name;
    private int idprinciple;
    private String principle;
    private String description;
    private String image;
    private String creationdate;
    private String updatedate;
    private boolean state ;

    public GuidelineModel(int idguideline, String name, int idprinciple, String principle, String description, String image, String creationdate, String updatedate, boolean state) {
        this.idguideline = idguideline;
        this.name = name;
        this.idprinciple = idprinciple;
        this.principle = principle;
        this.description = description;
        this.image = image;
        this.creationdate = creationdate;
        this.updatedate = updatedate;
        this.state = state;
    }

    public int getIdguideline() {
        return idguideline;
    }

    public void setIdguideline(int idguideline) {
        this.idguideline = idguideline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
