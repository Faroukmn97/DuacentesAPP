package com.example.duacentes.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.duacentes.R;
import com.example.duacentes.config.HttpsTrustManager;
import com.example.duacentes.config.general_data;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    /**
     * RequestQueue volley
     */

    private RequestQueue requestQueue;

    /**
     * Botones
     */
    private Button btnLogin, btnRegister;

    /**
     * EditTexts
     */

    private EditText edittextuser, edittextpassword;

    /**
     * variables para mantener sesion
     */
    private SharedPreferences preferences;
    private String user_token;

    /**
     *
     * Progreso
     */

    ProgressDialog proDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        proDialog = new ProgressDialog(this);
        proDialog.setTitle("Validando sesión");
        proDialog.setMessage("Cargando espere un momento...");
        proDialog.show();
        if(validatesesion()){
            proDialog.dismiss();
        }else{
            proDialog.dismiss();
            proDialog.dismiss();
        }



        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String user = edittextuser.getText().toString();
                String password = edittextpassword.getText().toString();
              try {
                  proDialog.setTitle("Iniciando sesión");
                  proDialog.setMessage("Cargando espere un momento...");
                  proDialog.show();

                    Session(user,password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                Bundle b = new Bundle();
                startActivity(intent);
            }
        });
    }

    /**
     * inicialización de componentes
     */

    private void init(){
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        btnLogin = findViewById(R.id.btnlogin);
        btnRegister = findViewById(R.id.btnregister);
        edittextuser =  (EditText) findViewById(R.id.user);
        edittextpassword =  (EditText)findViewById(R.id.password);

    }

    /**
     * Sessión del usuario
     */

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

    /**
     * Validación de la sessión del usuario
     * @return true si existe sessión o false si no hay sessión
     */

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

    /**
     * redirección al menú
     */
    private void goMenu(){
        Intent intent = new Intent(MainActivity.this, ProcessActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * SERVICES
     */

    /**
     *  SERVICES SESSION USER
     * @param user usuario
     * @param password contraseña
     * @throws JSONException
     */
    private void Session(String user, String password) throws JSONException {

        HttpsTrustManager.allowAllSSL();

        JSONObject data = new JSONObject();

        data.put("email", user);
        data.put("password", password);
        data.put("recuerdame", true);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,general_data.URL +"users/logIn",
                data, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("flag")){
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("iduser",response.getJSONObject("data").getString("iduser"));
                        editor.putString("names",response.getJSONObject("data").getString("names"));
                        editor.putString("last_name",response.getJSONObject("data").getString("last_name"));
                        editor.putString("email",response.getJSONObject("data").getString("email"));
                        editor.putString("image",response.getJSONObject("data").getString("image").replace("\\",""));
                        editor.putString("birthdate",response.getJSONObject("data").getString("birthdate"));
                        editor.putString("rol",response.getJSONObject("data").getString("rol"));
                        editor.putString("state",response.getJSONObject("data").getString("state"));
                        editor.putString("user_token",response.getJSONObject("data").getString("user_token"));
                        editor.commit();
                        goMenu();
                        proDialog.dismiss();
                    }else{
                        warningMessage("Credenciales incorrectas");
                        proDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    errorMessage("Ha ocurrido un error :)");
                    proDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", String.valueOf(error));
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("Accept", "application/json");
                return params;
            }
        };
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } else {
            requestQueue.add(request);
        }
    }


    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Excelente!")
                .setContentText(message).setCancelButton("Cerrar", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

    public void errorMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message)
                .show();
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok")
                .show();
    }

}