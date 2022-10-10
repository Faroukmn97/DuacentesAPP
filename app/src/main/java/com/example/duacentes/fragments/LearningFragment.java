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
import com.example.duacentes.adapters.PrinciplesLearningAdapter;
import com.example.duacentes.config.HttpsTrustManager;
import com.example.duacentes.config.general_data;
import com.example.duacentes.interfaces.iCommunicates_Fragments;
import com.example.duacentes.models.PrincipleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LearningFragment extends Fragment {

    /**
     * RequestQueue volley
     */

    private RequestQueue requestQueue;


    /**
     * Barra de progreso de carga
     */
    private ProgressDialog proDialogLearning;


    /**
     * variables para mantener sesion
     */
    private SharedPreferences preferences;
    private String email;
    private String user_token;

    /**
     * Lista modelo de principios
     */
    List<PrincipleModel> ListElementsPrinciple;

    /**
     * Recyclerview
     */
    private RecyclerView recyclerViewprinciple;

    /**
     * Adaptador de principios aprendizaje
     */
    private PrinciplesLearningAdapter principlesLearningAdapter;

    /**
     * Comunicación entre fragmentos
     */
    private iCommunicates_Fragments interfacecommunicates_Fragments;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_learning, container, false);
        init();
        sessionuser();

        proDialogLearning = ProgressDialog.show(getActivity(), "Cargando...", "Espere un momento...", true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewprinciple = view.findViewById(R.id.LRViewlearning);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewprinciple.setLayoutManager(layoutManager);
        recyclerViewprinciple.setHasFixedSize(true);

        this.getprinciples();
    }

    private void getprinciples() {

        HttpsTrustManager.allowAllSSL();

        /**
         * API
         */
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, general_data.URL + "principle/getprinciplelist",
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("flag")) {
                        proDialogLearning.dismiss();
                       // Log.d("respo", response.getString("message"));
                       // Log.d("data", response.getString("data"));
                        ListElementsPrinciple = new ArrayList<>();

                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ListElementsPrinciple.add(new PrincipleModel(
                                    object.getInt("idprinciple"),
                                    object.get("name").toString(),
                                    object.get("description").toString(),
                                    general_data.URLIMAG + object.get("image").toString().replace("\\", ""),
                                    object.get("creationdate").toString(),
                                    object.get("updatedate").toString(),
                                    object.getBoolean("state")));
                        }


                        principlesLearningAdapter = new PrinciplesLearningAdapter(ListElementsPrinciple, getActivity());
                        recyclerViewprinciple.setAdapter(principlesLearningAdapter);

                        principlesLearningAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(), ListElementsPrinciple.get(recyclerViewprinciple.getChildAdapterPosition(view)).getName(), Toast.LENGTH_SHORT).show();
                                //  Log.d("mode",ListElementsPrinciple.get(recyclerViewprinciple.getChildAdapterPosition(view)).toString());

                                interfacecommunicates_Fragments.SendPrincipleforPrincipleDetailLModel(ListElementsPrinciple.get(recyclerViewprinciple.getChildAdapterPosition(view)));
                            }
                        });

                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                        proDialogLearning.dismiss();
                    }
                } catch (
                        JSONException e) {
                    e.printStackTrace();
                    proDialogLearning.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", String.valueOf(error));
                proDialogLearning.dismiss();
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
                params.put("Authorization", "Bearer " + user_token);
                return params;
            }
        };
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(request);
        } else {
            requestQueue.add(request);
        }
    }


    /**
     * Sessión del usuario
     */

    public void sessionuser() {
        String names = preferences.getString("names", null);
        String last_name = preferences.getString("last_name", null);
        email = preferences.getString("email", null);
        String image = preferences.getString("image", null);
        String birthdate = preferences.getString("birthdate", null);
        String rol = preferences.getString("rol", null);
        String state = preferences.getString("state", null);
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

    private boolean validatesesion() {
        sessionuser();
        return email != null && user_token != null && user_token.equals("");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            /**
             * referencias para comunicar fragments
             */
            Activity activitys = (Activity) context;
            interfacecommunicates_Fragments = (iCommunicates_Fragments) activitys;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}