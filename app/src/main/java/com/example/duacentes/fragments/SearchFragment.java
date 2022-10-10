package com.example.duacentes.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import com.example.duacentes.adapters.SearchToolAdapter;
import com.example.duacentes.config.HttpsTrustManager;
import com.example.duacentes.config.general_data;
import com.example.duacentes.interfaces.iCommunicates_Fragments;
import com.example.duacentes.models.ToolModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {

    /**
     * RequestQueue volley
     */

    private RequestQueue requestQueue;


    /**
     * Barra de progreso de carga
     */
    private ProgressDialog proDialog;


    /**
     * variables para mantener sesion
     */
    private SharedPreferences preferences;
    private String iduser, names, last_name, email, image, birthdate, rol, state, user_token;

    /**
     * Lista modelo de herramientas
     */

    List<ToolModel> ListElementsSearchTool;

    /**
     * Recyclerview
     */
    private RecyclerView recyclerViewSearchTool;

    /**
     * Adaptador de herramientas de aprendizaje
     */
    private SearchToolAdapter searchToolAdapter;

    /**
     * Comunicación entre fragmentos
     */
    private iCommunicates_Fragments interfacecommunicates_Fragments;

    /**
     * referencias para comunicar fragments
     */
    private Activity activitys;

    private SearchView searchView;

    View view;

    private ProgressDialog proDialogSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        proDialogSearch = ProgressDialog.show(getActivity(), "Cargando...", "Espere un momento...", true);

        view = inflater.inflate(R.layout.fragment_search, container, false);

        init();

        sessionuser();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null) {
            view = view;
            searchView = view.findViewById(R.id.action_bar_spinner);

            recyclerViewSearchTool = view.findViewById(R.id.LRViewsearchtool);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerViewSearchTool.setLayoutManager(layoutManager);
            recyclerViewSearchTool.setHasFixedSize(true);


            ListElementsSearchTool = new ArrayList<>();

            ListElementsSearchTool.add(new ToolModel(0, "¿Qué recurso está buscando?", "https://fyc.uteq.edu.ec/duacentes/static/images/app/confundido_search.png", "", "", "Le recomendamos buscar con otras palabras claves", 0, "", 0, "", 0, "", "", "", true));
            searchToolAdapter = new SearchToolAdapter(ListElementsSearchTool, getActivity());
            recyclerViewSearchTool.setAdapter(searchToolAdapter);
           // this.getsearchtool("xxxxxxxxxxxxxx");
            searchView.setOnQueryTextListener(this);
            proDialogSearch.dismiss();
        }

    }

    private void getsearchtool(String filt) {

        HttpsTrustManager.allowAllSSL();

        filt = (filt == "" || filt.isEmpty()) ? "null" : filt;

        /**
         * API
         */
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, general_data.URL + "tool/getsearchtoolall?namediccionario=" + filt,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("flag")) {
                        //   Log.d("respo", response.getString("message"));
                        //   Log.d("data", response.getString("data"));

                        ListElementsSearchTool = new ArrayList<>();

                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ListElementsSearchTool.add(new ToolModel(
                                    object.getInt("idtool"),
                                    object.get("name").toString(),
                                    general_data.URLIMAG + object.get("image").toString().replace("\\", ""),
                                    object.get("urltool").toString(),
                                    object.get("diccionario").toString(),
                                    object.get("description").toString(),
                                    object.getInt("idprinciple"),
                                    object.get("principle").toString(),
                                    object.getInt("idguideline"),
                                    object.get("guideline").toString(),
                                    object.getInt("idresource"),
                                    object.get("resource").toString(),
                                    object.get("creationdate").toString(),
                                    object.get("updatedate").toString(),
                                    object.getBoolean("state")));
                        }


                        if (ListElementsSearchTool == null || ListElementsSearchTool.size() == 0) {
                            ListElementsSearchTool.add(new ToolModel(0, "Al parecer no se encontró información", "https://fyc.uteq.edu.ec/duacentes/static/images/app/sin_info.png", "", "", "Le recomendamos buscar con otras palabras claves", 0, "", 0, "", 0, "", "", "", true));
                        }

                        searchToolAdapter = new SearchToolAdapter(ListElementsSearchTool, getActivity());
                        recyclerViewSearchTool.setAdapter(searchToolAdapter);

                        searchToolAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                              //  Toast.makeText(getActivity(), ListElementsSearchTool.get(recyclerViewSearchTool.getChildAdapterPosition(view)).getUrltool(), Toast.LENGTH_SHORT).show();
                                if (ListElementsSearchTool.get(recyclerViewSearchTool.getChildAdapterPosition(view)).getIdprinciple() > 0) {
                                    interfacecommunicates_Fragments.SendToolModeltoSearchResourceDetail(ListElementsSearchTool.get(recyclerViewSearchTool.getChildAdapterPosition(view)));
                                }
                            }
                        });

                        proDialogSearch.dismiss();
                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                        proDialogSearch.dismiss();
                    }


                } catch (
                        JSONException e) {
                    e.printStackTrace();
                    proDialogSearch.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", String.valueOf(error));
                proDialogSearch.dismiss();
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
        if (iduser != null && email != null && user_token != null || user_token != "") {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.activitys = (Activity) context;
            interfacecommunicates_Fragments = (iCommunicates_Fragments) this.activitys;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        this.getsearchtool(s);
        searchToolAdapter.Filtering(s);
        return false;
    }
}