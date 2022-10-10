package com.example.duacentes.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

import com.example.duacentes.R;

public class SplashActivity extends AppCompatActivity {

    /**
     * variables para mantener sesion
     */
    private SharedPreferences preferences;
    private String iduser;
    private String names;
    private String last_name;
    private String email;
    private String image;
    private String rol;
    private String state;
    private String user_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.init();

        if(!validatesesion()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }

    }

    private void init(){
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
    }

    private boolean validatesesion(){
        sessionuser();
        if (user_token != null) {
            Log.d("token inicio", user_token);
            goMenu();
            return true;
        }else{
            return false;
        }
    }


    private void goMenu(){
        Intent intent = new Intent(SplashActivity.this, ProcessActivity.class);
        startActivity(intent);
        finish();
    }

    public void sessionuser(){
        iduser = preferences.getString("iduser",null);
        names= preferences.getString("names",null);
        last_name= preferences.getString("last_name",null);
        email= preferences.getString("email",null);
        image= preferences.getString("image",null);
        String birthdate = preferences.getString("birthdate", null);
        rol= preferences.getString("rol",null);
        state= preferences.getString("state",null);
        user_token= preferences.getString("user_token",null);
    }
}