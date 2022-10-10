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
        String iduser = preferences.getString("iduser", null);
        String names = preferences.getString("names", null);
        String last_name = preferences.getString("last_name", null);
        String email = preferences.getString("email", null);
        String image = preferences.getString("image", null);
        String birthdate = preferences.getString("birthdate", null);
        String rol = preferences.getString("rol", null);
        String state = preferences.getString("state", null);
        user_token= preferences.getString("user_token",null);
    }
}