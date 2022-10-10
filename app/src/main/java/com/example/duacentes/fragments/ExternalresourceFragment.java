package com.example.duacentes.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.duacentes.R;
import com.example.duacentes.activitys.MainActivity;
import com.example.duacentes.adapters.CheckpointLearningAdapter;
import com.example.duacentes.adapters.ExternalresourceAdapter;
import com.example.duacentes.config.HttpsTrustManager;
import com.example.duacentes.config.general_data;
import com.example.duacentes.interfaces.iCommunicates_Fragments;
import com.example.duacentes.models.CheckpointModel;
import com.example.duacentes.models.ExternalresourceModel;
import com.example.duacentes.models.GuidelineModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExternalresourceFragment extends Fragment {


    /**
     * RequestQueue volley
     */

    private RequestQueue requestQueue;

    /**
     * variables para mantener sesion
     */
    private SharedPreferences preferences;
    private String user_token;

    /**
     * Lista modelo de Recursos Externos
     */

    /**
     * Lista modelo de principios
     */
    List<ExternalresourceModel> ListElementsExternalresourceModel;

    /**
     * Comunicación entre fragmentos
     */
    private iCommunicates_Fragments interfacecommunicates_Fragments;

    /**
     * Recyclerview
     */
    private RecyclerView recyclerViewExternalresource;

    /**
     * Adaptador de recursos externos
     */
    private ExternalresourceAdapter externalresourceAdapter;

    private ProgressDialog proDialogExternalResource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        proDialogExternalResource = ProgressDialog.show(getActivity(), "Cargando...", "Espere un momento...", true);

        View inflate = inflater.inflate(R.layout.fragment_externalresource, container, false);
        init();
        sessionuser();

        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerViewExternalresource = view.findViewById(R.id.RViewresourceexternal);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewExternalresource.setLayoutManager(layoutManager);
        recyclerViewExternalresource.setHasFixedSize(true);
        this.getexternalresource();
    }


    private void getexternalresource(){

        HttpsTrustManager.allowAllSSL();

        /**
         * API
         */
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, general_data.URL + "externalresource/getexternalresources",
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("flag")) {
                     //   Log.d("respo", response.getString("message"));
                    //    Log.d("data", response.getString("data"));
                        ListElementsExternalresourceModel = new ArrayList<>();

                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ListElementsExternalresourceModel.add(new ExternalresourceModel(
                                    object.getInt("idexternalresource"),
                                    object.get("name").toString(),
                                    object.get("description").toString(),
                                    general_data.URLIMAG + object.get("image").toString().replace("\\",""),
                                    object.get("resource").toString(),
                                    object.get("creationdate").toString(),
                                    object.get("updatedate").toString(),
                                    object.getBoolean("state")));
                        }

                        externalresourceAdapter  = new ExternalresourceAdapter(ListElementsExternalresourceModel, getActivity());
                        recyclerViewExternalresource.setAdapter(externalresourceAdapter);

                        externalresourceAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                               // Toast.makeText(getActivity(),ListElementsExternalresourceModel.get(recyclerViewExternalresource.getChildAdapterPosition(view)).getName(),Toast.LENGTH_SHORT).show();
                                interfacecommunicates_Fragments.SendExternalResourceModel(ListElementsExternalresourceModel.get(recyclerViewExternalresource.getChildAdapterPosition(view)));
                            }
                        });

                        proDialogExternalResource.dismiss();

                    }else{
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                        proDialogExternalResource.dismiss();
                    }
                } catch(
                        JSONException e)

                {
                    e.printStackTrace();
                    proDialogExternalResource.dismiss();
                }

            }
        },new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse (VolleyError error){
                Log.d("Error.Response", String.valueOf(error));
                proDialogExternalResource.dismiss();
            }
        })

        {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders () throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer "+user_token);
                return params;
            }
        }

                ;
        if(requestQueue ==null)

        {
            requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(request);
        } else

        {
            requestQueue.add(request);
        }
    }

    /**
     * Sessión del usuario
     */

    public void sessionuser() {
        String iduser = preferences.getString("iduser", null);
        String email = preferences.getString("email", null);
        user_token = preferences.getString("user_token", null);
    }

    /**
     * inicialización de componentes
     */

    private void init() {

        preferences = this.getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);

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



    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof Activity){
            /**
             * referencias para comunicar fragments
             */
            Activity activitys = (Activity) context;
            interfacecommunicates_Fragments = (iCommunicates_Fragments) activitys;
        }
    }
    @Override
    public void onDetach(){
        super.onDetach();
    }



}