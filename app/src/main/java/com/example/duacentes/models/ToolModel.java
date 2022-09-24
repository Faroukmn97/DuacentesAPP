package com.example.duacentes.models;

import java.io.Serializable;

public class ToolModel implements Serializable {

    private int idtool;
    private String name;
    private String image;
    private String urltool;
    private String diccionario;
    private String description;
    private int idprinciple;
    private String principle;
    private int idguideline;
    private String guideline;
    private int idresource;
    private String resource;
    private String creationdate;
    private String updatedate;
    private boolean state;

    public ToolModel(int idtool, String name, String image, String urltool, String diccionario, String description, int idprinciple, String principle, int idguideline, String guideline, int idresource, String resource, String creationdate, String updatedate, boolean state) {
        this.idtool = idtool;
        this.name = name;
        this.image = image;
        this.urltool = urltool;
        this.diccionario = diccionario;
        this.description = description;
        this.idprinciple = idprinciple;
        this.principle = principle;
        this.idguideline = idguideline;
        this.guideline = guideline;
        this.idresource = idresource;
        this.resource = resource;
        this.creationdate = creationdate;
        this.updatedate = updatedate;
        this.state = state;
    }

    public int getIdtool() {
        return idtool;
    }

    public void setIdtool(int idtool) {
        this.idtool = idtool;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrltool() {
        return urltool;
    }

    public void setUrltool(String urltool) {
        this.urltool = urltool;
    }

    public String getDiccionario() {
        return diccionario;
    }

    public void setDiccionario(String diccionario) {
        this.diccionario = diccionario;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getIdresource() {
        return idresource;
    }

    public void setIdresource(int idresource) {
        this.idresource = idresource;
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


