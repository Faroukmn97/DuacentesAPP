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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.duacentes.adapters.CheckpointLearningAdapter;
import com.example.duacentes.config.HttpsTrustManager;
import com.example.duacentes.config.general_data;
import com.example.duacentes.interfaces.iCommunicates_Fragments;
import com.example.duacentes.models.CheckpointModel;
import com.example.duacentes.models.GuidelineModel;
import com.example.duacentes.models.PrincipleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LcheckpointFragment extends Fragment {

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
    List<CheckpointModel> ListElementsCheckpointModel;

    /**
     * Comunicación entre fragmentos
     */
    private iCommunicates_Fragments interfacecommunicates_Fragments;

    /**
     * Recyclerview
     */
    private RecyclerView recyclerViewCheckpoint;

    /**
     * Adaptador de principios aprendizaje
     */
    private CheckpointLearningAdapter checkpointLearningAdapter;

    /**
     * referencias para comunicar fragments
     */
    private Activity activitys;

    /**
     * Textview Nombre del princicip
     */
    private TextView nameguidelinecheck;

    private ImageView imgguidelinecheck;

    /**
     * Modelo principio
     */
    private PrincipleModel principleModel;

    /**
     * Boton de regresar a pautas
     */
    private TextView textvbuttonguidelinecheck;

    /**
     * Boton de regresar a principios
     */

    private TextView textvbuttonprinciplecheck;

    private ImageView Imageprincipleguide;
    /**
     * Contiene las imagenes de los cerebros de los principios
     */
    private int[] Icons = {
            R.drawable.cerebrorepresentacion,
            R.drawable.cerebroaccexpresion,
            R.drawable.cerebrocompromiso,
    };

    private int[] backgroundscheck = {
            R.drawable.cardlearningrepresentation,
            R.drawable.cardlearningacex,
            R.drawable.cardlearningengagement,
    };

    private int[] backgroundsbuttons = {
            R.drawable.buttonlearningrepresentation,
            R.drawable.buttonlearningacex,
            R.drawable.buttonlearningengagement,
    };

    /**
     * Textview Nombre del princicio
     */
    private TextView namepguideline;

    private LinearLayout linearLayoutcheck;

    /**
     * Barra de progreso de carga
     */
    private ProgressDialog proDialogLearning;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lcheckpoint, container, false);

        init();

        sessionuser();

        proDialogLearning = ProgressDialog.show(getActivity(), "Cargando...", "Espere un momento...", true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Bundle object= getArguments();
        GuidelineModel guidelineModel = null;
        if(object!=null){
            guidelineModel = (GuidelineModel) object.getSerializable("object");
            principleModel = (PrincipleModel) object.getSerializable("object2");

            nameguidelinecheck = (TextView) view.findViewById(R.id.nameguidelinecheck);
            nameguidelinecheck.setText(guidelineModel.getName());


            namepguideline = (TextView) view.findViewById(R.id.namepguidelinecheck);
            namepguideline.setText(principleModel.getName());

            Imageprincipleguide = (ImageView) view.findViewById(R.id.Imageprincipleguidecheck);
            Imageprincipleguide.setBackgroundResource(Icons[principleModel.getIdprinciple() - 1]);


            recyclerViewCheckpoint= view.findViewById(R.id.LRViewcheckpoint);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerViewCheckpoint.setLayoutManager(layoutManager);
            recyclerViewCheckpoint.setHasFixedSize(true);

            textvbuttonguidelinecheck = view.findViewById(R.id.textvbuttonguidelinecheck);

            textvbuttonprinciplecheck = view.findViewById(R.id.textvbuttonprinciplecheck);

            textvbuttonguidelinecheck.setBackgroundResource(backgroundsbuttons[principleModel.getIdprinciple() - 1]);

            this.getlcheckpoint(guidelineModel);

        }

        textvbuttonguidelinecheck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                interfacecommunicates_Fragments.SendDetailPrincipleforGuidelineModel(principleModel);
            }
        });

        textvbuttonprinciplecheck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out)
                        .replace(R.id.contentf, new LearningFragment()).addToBackStack(null).commit();
            }
        });


    }

    private void getlcheckpoint(GuidelineModel guidelineModel){

        HttpsTrustManager.allowAllSSL();

        /**
         * API
         */
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, general_data.URL + "checkpoint/getcheckpointpgbyguideid?idguideline="+guidelineModel.getIdguideline(),
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("flag")) {
                        proDialogLearning.dismiss();
                     //   Log.d("respo", response.getString("message"));
                      //  Log.d("data", response.getString("data"));
                        ListElementsCheckpointModel = new ArrayList<>();

                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ListElementsCheckpointModel.add(new CheckpointModel(
                                    object.getInt("idcheckpoint"),
                                    object.getInt("idprinciple"),
                                    object.get("principle").toString(),
                                    object.getInt("idguideline"),
                                    object.get("guideline").toString(),
                                    object.get("name").toString(),
                                    object.get("description").toString(),
                                    general_data.URLIMAG + object.get("image").toString().replace("\\",""),
                                    object.get("creationdate").toString(),
                                    object.get("updatedate").toString(),
                                    object.getBoolean("state")));
                        }


                        checkpointLearningAdapter  = new CheckpointLearningAdapter(ListElementsCheckpointModel, getActivity());
                        recyclerViewCheckpoint.setAdapter(checkpointLearningAdapter);

                        checkpointLearningAdapter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(),ListElementsCheckpointModel.get(recyclerViewCheckpoint.getChildAdapterPosition(view)).getName(),Toast.LENGTH_SHORT).show();
                                interfacecommunicates_Fragments.SendCheckpointforCheckpointDetail(ListElementsCheckpointModel.get(recyclerViewCheckpoint.getChildAdapterPosition(view)),principleModel);
                            }
                        });

                    }else{
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                        proDialogLearning.dismiss();
                    }
                } catch(
                        JSONException e)

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
        if (iduser != null && email != null && user_token != null || user_token.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof Activity){
            this.activitys = (Activity) context;
            interfacecommunicates_Fragments = (iCommunicates_Fragments) this.activitys;
        }
    }
    @Override
    public void onDetach(){
        super.onDetach();
    }


}