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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.duacentes.adapters.ToolinterseachAdapter;
import com.example.duacentes.config.HttpsTrustManager;
import com.example.duacentes.config.general_data;
import com.example.duacentes.interfaces.iCommunicates_Fragments;
import com.example.duacentes.models.GuidelineModel;
import com.example.duacentes.models.PrincipleModel;
import com.example.duacentes.models.ToolModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ISresourcesFragment extends Fragment {

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

    List<ToolModel> ListElementsISTool;

    /**
     * Recyclerview
     */
    private RecyclerView recyclerViewISTool;

    /**
     * Adaptador de herramientas de aprendizaje
     */
    private ToolinterseachAdapter ISToolAdapter;

    /**
     * Comunicación entre fragmentos
     */
    private iCommunicates_Fragments interfacecommunicates_Fragments;

    /**
     * referencias para comunicar fragments
     */
    private Activity activitys;

    /**
     * Modelo principio
     */
    private PrincipleModel principleModel;

    /**
     * Boton de regresar a pautas
     */
    Button returnbtnisresources;

    private ImageView Imageprincipleguide;
    /**
     * Contiene las imagenes de los cerebros de los principios
     */
    private int[] Icons = {
            R.drawable.cerebrorepresentacion,
            R.drawable.cerebroaccexpresion,
            R.drawable.cerebrocompromiso,
    };

    /**
     * Textview Nombre del princicio
     */
    private TextView namepguidelineresource;

    /**
     * Texview Nombre de la pauta
     */

    private TextView nameguidelineresource;

    private ProgressDialog proDialogIS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        proDialogIS = ProgressDialog.show(getActivity(), "Cargando...", "Espere un momento...", true);

        View inflate = inflater.inflate(R.layout.fragment_i_sresources, container, false);

        init();

        sessionuser();
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Bundle object = getArguments();
        GuidelineModel guidelineModel = null;

        if (getActivity() != null) {

            if (object != null) {
                guidelineModel = (GuidelineModel) object.getSerializable("object");
                principleModel = (PrincipleModel) object.getSerializable("object2");
             //   Log.d("name", guidelineModel.getName());

                namepguidelineresource = (TextView) view.findViewById(R.id.namepguidelineresource);
                namepguidelineresource.setText(principleModel.getName());

                nameguidelineresource = (TextView) view.findViewById(R.id.nameguidelineresource);
                nameguidelineresource.setText(guidelineModel.getName());

                Imageprincipleguide = (ImageView) view.findViewById(R.id.Imageprincipleguideresource);
                Imageprincipleguide.setBackgroundResource(Icons[principleModel.getIdprinciple() - 1]);

                recyclerViewISTool = view.findViewById(R.id.LRViewisresource);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerViewISTool.setLayoutManager(layoutManager);
                recyclerViewISTool.setHasFixedSize(true);
              //  returnbtnisresources = view.findViewById(R.id.returnbtnisresources);

                this.getsearchtool(guidelineModel.getIdguideline());
            } else {
                Log.d("ISResources", "sin datos");
            }

        /*    returnbtnisresources.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfacecommunicates_Fragments.SendISPrincipleModel(principleModel);
                }
            });*/

        }


    }


    private void getsearchtool(int idguideline) {

        HttpsTrustManager.allowAllSSL();

        /**
         * API
         */
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, general_data.URL + "tool/getsearchtoolbyguideline?idguideline=" + idguideline,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("flag")) {
                    //    Log.d("respo", response.getString("message"));
                   //     Log.d("data", response.getString("data"));
                        ListElementsISTool = new ArrayList<>();

                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ListElementsISTool.add(new ToolModel(
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

                        if (ListElementsISTool == null || ListElementsISTool.size() == 0) {
                            ListElementsISTool.add(new ToolModel(0, "Al parecer no se encontró información", "https://fyc.uteq.edu.ec/duacentes/static/images/app/fantasmaasustado.png", "", "", "Le recomendamos buscar con otras palabras claves", 0, "", 0, "", 0, "", "", "", true));
                        }

                        ISToolAdapter = new ToolinterseachAdapter(ListElementsISTool, getActivity());
                        recyclerViewISTool.setAdapter(ISToolAdapter);

                        ISToolAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(), ListElementsISTool.get(recyclerViewISTool.getChildAdapterPosition(view)).getUrltool(), Toast.LENGTH_SHORT).show();
                                if (ListElementsISTool.get(recyclerViewISTool.getChildAdapterPosition(view)).getIdprinciple() > 0) {
                                    interfacecommunicates_Fragments.SendToolModel(ListElementsISTool.get(recyclerViewISTool.getChildAdapterPosition(view)));
                                }

                            }
                        });

                        proDialogIS.dismiss();

                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();

                        proDialogIS.dismiss();
                    }
                } catch (
                        JSONException e) {
                    e.printStackTrace();

                    proDialogIS.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", String.valueOf(error));
                proDialogIS.dismiss();
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
}
