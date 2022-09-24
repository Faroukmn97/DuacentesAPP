package com.example.duacentes.fragments;

import static java.lang.Thread.sleep;

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
import com.example.duacentes.adapters.GuidelineLearningAdapter;
import com.example.duacentes.config.HttpsTrustManager;
import com.example.duacentes.config.general_data;
import com.example.duacentes.interfaces.iCommunicates_Fragments;
import com.example.duacentes.models.GuidelineModel;
import com.example.duacentes.models.PrincipleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LguidelineFragment extends Fragment {

    /**
     * RequestQueue volley
     */

    private RequestQueue requestQueue;

    /**
     * variables para mantener sesion
     */
    private SharedPreferences preferences;
    private String iduser,email,user_token;

    /**
     * Lista modelo de principios
     */
    List<GuidelineModel> ListElementsGuideline;

    /**
     * Comunicación entre fragmentos
     */
    private iCommunicates_Fragments interfacecommunicates_Fragments;

    /**
     * Recyclerview
     */
    private RecyclerView recyclerViewguideline;

    /**
     * Adaptador de principios aprendizaje
     */
    private GuidelineLearningAdapter guidelinesLearningAdapter;


    /**
     * referencias para comunicar fragments
     */
    private Activity activitys;


    /**
     * Contiene las imagenes de los cerebros de los principios
     */
    private int[] Icons = {
            R.drawable.cerebrorepresentacion,
            R.drawable.cerebroaccexpresion,
            R.drawable.cerebrocompromiso,
    };

    /**
     * Barra de progreso de carga
     */
    private ProgressDialog proDialogLearning;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lguideline, container, false);

        init();

        sessionuser();

        proDialogLearning = ProgressDialog.show(getActivity(), "Cargando...", "Espere un momento...", true);



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle object = getArguments();
        /**
         * Modelo principio
         */
        PrincipleModel principleModel = null;
        if (object != null) {
            principleModel = (PrincipleModel) object.getSerializable("object");

         //   namepguideline = (TextView) view.findViewById(R.id.namepguideline);
        //    namepguideline.setText(principleModel.getName());

         //   Imageprincipleguide = (ImageView) view.findViewById(R.id.Imageprincipleguide);
          //  Imageprincipleguide.setBackgroundResource(Icons[principleModel.getIdprinciple() - 1]);

            recyclerViewguideline = view.findViewById(R.id.LRViewlguideline);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerViewguideline.setLayoutManager(layoutManager);
            recyclerViewguideline.setHasFixedSize(true);

            getguideline(principleModel);
        }
    }

    private void getguideline(PrincipleModel principleModel){

        HttpsTrustManager.allowAllSSL();

        /**
         * API
         */
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, general_data.URL + "guidelines/getguidelinesprinciplebyidprinciple?idprinciple="+principleModel.getIdprinciple(),
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("flag")) {

                       // Log.d("respo", response.getString("message"));
                      //  Log.d("data", response.getString("data"));
                        ListElementsGuideline = new ArrayList<>();

                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ListElementsGuideline.add(new GuidelineModel(
                                    object.getInt("idguideline"),
                                    object.get("name").toString(),
                                    object.getInt("idprinciple"),
                                    object.get("principle").toString(),
                                    object.get("description").toString(),
                                    general_data.URLIMAG + object.get("image").toString().replace("\\",""),
                                    object.get("creationdate").toString(),
                                    object.get("updatedate").toString(),
                                    object.getBoolean("state")));
                        }

                        guidelinesLearningAdapter  = new GuidelineLearningAdapter(ListElementsGuideline, getActivity());
                        recyclerViewguideline.setAdapter(guidelinesLearningAdapter);

                        guidelinesLearningAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(),ListElementsGuideline.get(recyclerViewguideline.getChildAdapterPosition(view)).getName(),Toast.LENGTH_SHORT).show();
                                interfacecommunicates_Fragments.SendGuidelineforGuidelineDetailModel(ListElementsGuideline.get(recyclerViewguideline.getChildAdapterPosition(view)),principleModel);
                            }
                        });

                    }else{
                        proDialogLearning.dismiss();
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                    }

                    sleep(0);
                    proDialogLearning.dismiss();
                } catch(JSONException | InterruptedException e)

                {
                    e.printStackTrace();
                    proDialogLearning.dismiss();
                }

            }
        },new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse (VolleyError error){
                Log.d("Error.Response", String.valueOf(error));
                proDialogLearning.dismiss();
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
        iduser = preferences.getString("iduser", null);
        email = preferences.getString("email", null);
        user_token = preferences.getString("user_token", null);
    }

    /**
     * inicialización de componentes
     */

    private void init() {

        preferences = this.getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);

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