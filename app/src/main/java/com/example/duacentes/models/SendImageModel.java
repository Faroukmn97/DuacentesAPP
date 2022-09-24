package com.example.duacentes.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendImageModel {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("flag")
    @Expose
    private Boolean flag;
    @SerializedName("data")
    @Expose
    private String data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
