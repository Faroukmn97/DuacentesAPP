package com.example.duacentes.config;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONObject;

public class ProcessUpdateProfile extends
        AsyncTask<String, String, JSONObject> {

    private ProgressDialog nDialog;
    private Context fragm;

    public ProcessUpdateProfile(Context fragm){
        this.fragm = fragm;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        nDialog = new ProgressDialog(fragm);
        nDialog.setMessage("Loading..");
        nDialog.setTitle("Checking Network");
        nDialog.setIndeterminate(false);
       // nDialog.setCancelable(true);
        nDialog.show();

    }



    @Override
    protected JSONObject doInBackground(String... strings) {
        return null;
    }
}