package com.example.duacentes.activitys;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.duacentes.R;
import com.example.duacentes.config.HttpsTrustManager;
import com.example.duacentes.config.general_data;
import com.example.duacentes.config.utilities;
import com.example.duacentes.interfaces.ApiInterface;
import com.example.duacentes.models.SendImageModel;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    /**
     * RequestQueue volley
     */

    private RequestQueue requestQueue;

    /**
     * Variable que mantendr?? el archivo File (imagen de usuario)
     */
    private File f;

    /**
     * Botones para cargar imagen y enviar a guardar los datos al servidor y base de datos
     */
    private Button bondsmen, btnRegister;

    /**
     * Imagen de ususario
     */
    private CircleImageView imageUser;

    /**
     * Editexts
     */
    private EditText edtNameUser, lastname, lastnamemother, edtbirthdateUser, edtEmailUser, edtPasswordUser;

    private TextInputLayout txtInputNameUser;
    private TextInputLayout txtInputAppliedPatternU;
    private TextInputLayout txtInputAppliedMaternalU;
    private TextInputLayout txtInputbirthdate;
    private TextInputLayout txtInputEmailUser;
    private TextInputLayout txtInputPasswordUser;

    private int dia, mes, anio;

    String nombres, apellidoPaterno, apellidoMaterno, birthdate, correo, password;


    String realp;

    private final static int LOCATION_REQUEST_CODE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        init();


        edtbirthdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fechNacimiento();
            }
        });

        bondsmen.setOnClickListener(v -> {
            this.cargarImagen();
        });
        btnRegister.setOnClickListener(v -> {
            try {
                if (validar()) {
                    this.guardarDatos(nombres, apellidoPaterno + " " + apellidoMaterno, correo, password, birthdate);
                } else {
                    errorMessage("Por favor, complete todos los campos del formulario");
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
    }

    private boolean validar() {
        boolean retorno = true;
        nombres = edtNameUser.getText().toString();
        apellidoPaterno = lastname.getText().toString();
        apellidoMaterno = lastnamemother.getText().toString();
        birthdate = edtbirthdateUser.getText().toString();
        correo = edtEmailUser.getText().toString();
        password = edtPasswordUser.getText().toString();
        if (this.f == null) {
            errorMessage("debe selecionar una foto de perfil");
            retorno = false;
        }
        if (nombres.isEmpty()) {
            txtInputNameUser.setError("Ingresar nombres");
            retorno = false;
        } else {
            txtInputNameUser.setErrorEnabled(false);
        }
        if (apellidoPaterno.isEmpty()) {
            txtInputAppliedPatternU.setError("Ingresar apellido paterno");
            retorno = false;
        } else {
            txtInputAppliedPatternU.setErrorEnabled(false);
        }
        if (apellidoMaterno.isEmpty()) {
            txtInputAppliedMaternalU.setError("Ingresar apellido materno");
            retorno = false;
        } else {
            txtInputAppliedMaternalU.setErrorEnabled(false);
        }
        if (birthdate.isEmpty()) {
            txtInputbirthdate.setError("Ingresar apellido paterno");
            retorno = false;
        } else {
            txtInputbirthdate.setErrorEnabled(false);
        }
        if (correo.isEmpty()) {
            txtInputEmailUser.setError("Ingresar correo electr??nico");
            retorno = false;
        } else {
            txtInputEmailUser.setErrorEnabled(false);
        }
        if (password.isEmpty()) {
            txtInputPasswordUser.setError("Ingresar clave para el inicio de sesi??n");
            retorno = false;
        } else {
            txtInputPasswordUser.setErrorEnabled(false);
        }
        return retorno;
    }

    /**
     * Permisos storage
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Gracias por conceder los permisos para " +
                            "leer el almacenamiento, estos permisos son necesarios para poder " +
                            "escoger tu foto de perfil", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No podemos realizar el registro si no nos concedes los permisos para leer el almacenamiento.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * iniciar componentes
     */
    private void init() {
        txtInputNameUser = (TextInputLayout) findViewById(R.id.txtInputNameUser);
        txtInputAppliedPatternU = (TextInputLayout) findViewById(R.id.txtInputApellidoPaternoU);
        txtInputAppliedMaternalU = (TextInputLayout) findViewById(R.id.txtInputApellidoMaternoU);
        txtInputbirthdate = (TextInputLayout) findViewById(R.id.txtInputbirthdate);
        txtInputEmailUser = (TextInputLayout) findViewById(R.id.txtInputEmailUser);
        txtInputPasswordUser = (TextInputLayout) findViewById(R.id.txtInputPasswordUser);

        edtNameUser = (EditText) findViewById(R.id.edtNameUser);
        lastname = (EditText) findViewById(R.id.edtApellidoPaternoU);
        lastnamemother = (EditText) findViewById(R.id.edtApellidoMaternou);
        edtbirthdateUser = (EditText) findViewById(R.id.edtbirthdateUser);
        edtEmailUser = (EditText) findViewById(R.id.edtEmailUser);
        edtPasswordUser = (EditText) findViewById(R.id.edtPasswordUser);

        bondsmen = (Button) findViewById(R.id.btnSubirImagen);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        imageUser = (CircleImageView) findViewById(R.id.imageUser);
    }

    /**
     * Fecha de nacimiento
     */
    private void fechNacimiento() {

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                final String selectedDate = year + "-" + utilities.twoDigits(month + 1) + "-" + utilities.twoDigits(dayOfMonth);
                edtbirthdateUser.setText(selectedDate);
            }
        }, anio, mes, dia);
        datePickerDialog.show();
    }

    /**
     * Cargar imagen de usuario
     */
    private void cargarImagen() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/");
        startActivityForResult(Intent.createChooser(i, "Seleccione la Aplicaci??n"), 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            final String realPath = getRealPathFromURI(uri);
            this.f = new File(realPath);
            this.realp = realPath;
            this.imageUser.setImageURI(uri);
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String result;
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            result = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void guardarDatos(String names, String last_name, String email, String password, String birthdate) throws UnsupportedEncodingException {

        HttpsTrustManager.allowAllSSL();

        if (f != null) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(general_data.URL)
                    .client(getUnsafeOkHttpClient().build())
                    .addConverterFactory(GsonConverterFactory.create()).build();

            String ext = this.f.getName().replaceAll("^.*\\.(.*)$", "$1");

            MultipartBody.Part body = MultipartBody.Part.createFormData("file", "", RequestBody.create(MediaType.parse("image/" + ext), this.f));

            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            Call<SendImageModel> call = apiInterface.updateimage(body);
            call.enqueue(new Callback<SendImageModel>() {
                @Override
                public void onResponse(Call<SendImageModel> call, Response<SendImageModel> response) {
                    if (response.body().getFlag()) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject data = new JSONObject();
                            data.put("idrole", 2);
                            data.put("names", names);
                            data.put("last_name", last_name);
                            data.put("email", email);
                            data.put("password", password);
                            data.put("image", response.body().getData());
                            data.put("birthdate", birthdate);
                            data.put("state", true);

                            PURegistrationD(data);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SendImageModel> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("sale", realp);
            Log.d("saleim", f.getName());
        }
    }

    private void PURegistrationD(JSONObject data) throws JSONException {
        HttpsTrustManager.allowAllSSL();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, general_data.URL + "users/PostUserRegistrationDocente",
                data, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("flag")) {

                        successMessage("Usuario registrado");


                    } else {
                        warningMessage(response.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    errorMessage("Se ha producido un error : " + e.getMessage());
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", String.valueOf(error));
            }
        }) {
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

    /**
     * Validar verificaci??n HTTPS (CERTIFICADO) SSL CONECCI??N
     *
     * @return
     */
    public static OkHttpClient.Builder getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            Log.d("checkClientTrusted", "checkClientTrusted");
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                            Log.d("checkServerTrusted", "checkServerTrusted");
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void successMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Excelente!")
                .setContentText(message)
                .setConfirmButton("Ok", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();


    }

    public void errorMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message).show();
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(this,
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificaci??n del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
    }

}
