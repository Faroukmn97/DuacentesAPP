package com.example.duacentes.fragments.externalresource;

import static java.lang.Thread.sleep;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.duacentes.R;
import com.example.duacentes.activitys.MainActivity;
import com.example.duacentes.config.general_data;
import com.example.duacentes.models.ExternalresourceModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;


public class ExternalresourceDetailFragment extends Fragment {

    /**
     * RequestQueue volley
     */

    private RequestQueue requestQueue;

    /**
     * variables para mantener sesion
     */
    private SharedPreferences preferences;
    private String iduser, names, last_name, email, image, birthdate, rol, state, user_token;


    /**
     * Bloque 1
     */
    ShapeableImageView imageresourceexternal;
    TextView nameexternalresource;
    TextView descriptionexternalresource;

    /**
     * Bloque 2
     */
    TextView linkresourceexternal;
    MaterialButton btngotoresourceexternal;
    DownloadManager manager;

    private long downloadId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_externalresource_detail, container, false);
        this.init(inflate);

        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        Bundle object = getArguments();
        ExternalresourceModel externalresourceModel;
        if (object != null) {
            externalresourceModel = (ExternalresourceModel) object.getSerializable("object");

            Glide.with(this).load(externalresourceModel.getImage().replace('\\', '/'))
                    .placeholder(R.drawable.progress_animation)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .error(R.drawable.try_later)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    }).into(imageresourceexternal);

            nameexternalresource.setText(externalresourceModel.getName());
       //     linkresourceexternal.setText(general_data.URLIMAG + externalresourceModel.getResource());

            descriptionexternalresource.setText(externalresourceModel.getDescription());

            btngotoresourceexternal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Uri uri = Uri.parse(general_data.URLIMAG + externalresourceModel.getResource().toString().replace("\\", ""));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);*/

                 //   Log.d("PDFLINK", general_data.URLIMAG + externalresourceModel.getResource().toString().replace("\\", ""));
                    downloadFile(externalresourceModel);
                }
            });
        }
    }


    /**
     * DOW
     */

    public void downloadFile(ExternalresourceModel externalresourceModel) {
        String urldownload = general_data.URLIMAG + externalresourceModel.getResource();
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("cargando Archivo...");



        new DownloadFileAsyncTask(progressDialog).execute(urldownload);
    }


    class DownloadFileAsyncTask extends AsyncTask<String, Integer, String> {
    ProgressDialog progressDialog;
        DownloadFileAsyncTask(ProgressDialog progressDialog){
            this.progressDialog = progressDialog;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... urlfile) {
            String urlDownload = urlfile[0];
            HttpURLConnection connection = null;
            InputStream input = null;
            OutputStream output = null;

            try {
                URL url = new URL(urlDownload);
                Log.d("url", url.toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Error connection";
                }
                int tamanoFile = connection.getContentLength();
                input = connection.getInputStream();
                String rutFIleSaved = getContext().getExternalFilesDir("/files/") + FilenameUtils.getName(url.getPath());


             //   Log.d("Ruta del archivo", rutFIleSaved);
                output = new FileOutputStream(rutFIleSaved);
                byte[] data = new byte[1024];
                int count = 0;
                int total = 0;
                while ((count = input.read(data)) != -1) {
                    sleep(0);
                    output.write(data, 0, count);
                    total += count;
                    publishProgress((int) (total * 100 / tamanoFile));
                }
                File File = new File(rutFIleSaved);//File path

                String ext = android.webkit.MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File).toString());
                String type = android.webkit.MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);

                // Formato archivos
                type = (type.equals("vnd.openxmlformats-officedocument.spreadsheetml.sheet"))?"xlsx":type;
                type = (type.equals("vnd.ms-excel"))?"xls":type;
                type = (type.equals("plain"))?"txt":type;

                type = (type.equals("vnd.openxmlformats-officedocument.wordprocessingml.document"))?"docx":type;
                type = (type.equals("msword"))?"doc":type;
                type = (type.equals("vnd.ms-powerpoint"))?"ppt":type;
                type = (type.equals("vnd.openxmlformats-officedocument.presentationml.presentation"))?"pptx":type;


                File.renameTo(new File(FilenameUtils.getName(url.getPath() + ext)));


                Uri photoURI = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider",File);

                if (File.exists()){ //Revisa si el archivo existe!
                    Log.d("URI",photoURI.toString());
                    Log.d("Extension",type);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(photoURI, type);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "No existe archivo! ", Toast.LENGTH_SHORT).show();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {

                try {
                    if (input != null) { input.close(); }
                    if (output != null) { output.close(); }
                    if (connection != null) { connection.disconnect(); }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "Carga completa";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String message) {
            super.onPostExecute(message);
            progressDialog.dismiss();
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private static Intent getTxtFileIntent(String filePath, Context context,String type) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // El método getUri se ha escrito a continuación
        Uri uri=getUri(intent,filePath,context);
        intent.setDataAndType(uri, type);
        return intent;
    }

    /**
     * Obtener el Uri del archivo correspondiente
     * Intención de @param Intención correspondiente
     * @param param path
     * @return
     */
    private static Uri getUri(Intent intent, String param,Context context) {
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Juzgar si la versión es superior a 7.0
            uri =
                    FileProvider.getUriForFile(context,
                            context.getPackageName() + ".FileProvider",
                            new File(param));
            // Agregue esta oración para autorizar temporalmente el archivo representado por la Uri a la aplicación de destino
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(new File(param));
        }
        return uri;
    }


    /**
     * Sessión del usuario
     */

    public void sessionuser() {
        iduser = preferences.getString("iduser", null);
        names = preferences.getString("names", null);
        last_name = preferences.getString("last_name", null);
        email = preferences.getString("email", null);
        image = preferences.getString("image", null);
        birthdate = preferences.getString("birthdate", null);
        rol = preferences.getString("rol", null);
        state = preferences.getString("state", null);
        user_token = preferences.getString("user_token", null);
    }

    /**
     * inicialización de componentes
     */

    private void init(View view) {

        preferences = this.getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        // bloque 1
        imageresourceexternal = (ShapeableImageView) view.findViewById(R.id.imageresourceexternal);
        nameexternalresource = (TextView) view.findViewById(R.id.nameexternalresource);
        descriptionexternalresource = (TextView) view.findViewById(R.id.descriptionexternalresource);
        // bloque 2
      //  linkresourceexternal = (TextView) view.findViewById(R.id.linkresourceexternal);
        btngotoresourceexternal = (MaterialButton) view.findViewById(R.id.btngotoresourceexternal);


    }

    /**
     * Login
     */

    private void gologin() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        // bandera para que no se creen nuevas actividades innecesarias
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private boolean validatesesion() {
        sessionuser();
        if (iduser != null && email != null && user_token != null || user_token != "") {
            return true;
        } else {
            return false;
        }
    }

}