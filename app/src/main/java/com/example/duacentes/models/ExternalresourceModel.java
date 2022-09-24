package com.example.duacentes.models;

import java.io.Serializable;

public class ExternalresourceModel implements Serializable {

    private int idexternalresource;
    private String name;
    private String description;
    private String image;
    private String resource;
    private String creationdate;
    private String updatedate;
    private boolean state;

    public ExternalresourceModel(int idexternalresource, String name, String description, String image, String resource, String creationdate, String updatedate, boolean state) {
        this.idexternalresource = idexternalresource;
        this.name = name;
        this.description = description;
        this.image = image;
        this.resource = resource;
        this.creationdate = creationdate;
        this.updatedate = updatedate;
        this.state = state;
    }

    public int getIdexternalresource() {
        return idexternalresource;
    }

    public void setIdexternalresource(int idexternalresource) {
        this.idexternalresource = idexternalresource;
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
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
