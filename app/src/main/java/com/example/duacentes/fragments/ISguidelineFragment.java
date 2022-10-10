package com.example.duacentes.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.duacentes.adapters.GuidelineintersearchAdapter;
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

public class ISguidelineFragment extends Fragment {

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
    private RecyclerView recyclerViewISguideline;

    /**
     * Adaptador de principios aprendizaje
     */
    private GuidelineintersearchAdapter guidelineintersearchAdapter;


    /**
     * referencias para comunicar fragments
     */
    private Activity activitys;

    /**
     * Textview Nombre del princicip
     */
    private TextView nameispguideline;


    /**
     * Modelo principio
     */
    private PrincipleModel principleModel;

    private ImageView Imageprincipleguide;
    /**
     * Contiene las imagenes de los cerebros de los principios
     */
    private int[] Icons = {
            R.drawable.cerebrorepresentacion,
            R.drawable.cerebroaccexpresion,
            R.drawable.cerebrocompromiso,
    };

    private ProgressDialog proDialogIS;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        proDialogIS = ProgressDialog.show(getActivity(), "Cargando...", "Espere un momento...", true);
        View inflate = inflater.inflate(R.layout.fragment_i_sguideline, container, false);
        init();

        sessionuser();
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (getActivity() != null) {
            Bundle object = getArguments();
            principleModel = null;
            if (object != null) {
                principleModel = (PrincipleModel) object.getSerializable("object");
                nameispguideline = (TextView) view.findViewById(R.id.nameispguideline);
                nameispguideline.setText(principleModel.getName());

                Imageprincipleguide = (ImageView) view.findViewById(R.id.Imageispguide);
                Imageprincipleguide.setBackgroundResource(Icons[principleModel.getIdprinciple() - 1]);

                recyclerViewISguideline = view.findViewById(R.id.LRViewisguideline);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerViewISguideline.setLayoutManager(layoutManager);
                recyclerViewISguideline.setHasFixedSize(true);

                this.getguidelineintersearch(principleModel);

            } else {
                Log.d("ISGuidelines", "sin datos");
            }
        }

    }

    private void getguidelineintersearch(PrincipleModel principleModel) {
        HttpsTrustManager.allowAllSSL();
        /**
         * API
         */
        Log.d("name", principleModel.getName());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, general_data.URL + "guidelines/getguidelinesprinciplebyidprinciple?idprinciple=" + principleModel.getIdprinciple(),
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
                                    general_data.URLIMAG + object.get("image").toString().replace("\\", ""),
                                    object.get("creationdate").toString(),
                                    object.get("updatedate").toString(),
                                    object.getBoolean("state")));
                        }

                        guidelineintersearchAdapter = new GuidelineintersearchAdapter(ListElementsGuideline, getActivity());
                        recyclerViewISguideline.setAdapter(guidelineintersearchAdapter);

                        guidelineintersearchAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                             //   Toast.makeText(getActivity(), ListElementsGuideline.get(recyclerViewISguideline.getChildAdapterPosition(view)).getName(), Toast.LENGTH_SHORT).show();
                                interfacecommunicates_Fragments.SendISGuidelineModel(ListElementsGuideline.get(recyclerViewISguideline.getChildAdapterPosition(view)), principleModel);
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