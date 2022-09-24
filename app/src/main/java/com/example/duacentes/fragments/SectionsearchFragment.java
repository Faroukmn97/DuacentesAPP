package com.example.duacentes.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.duacentes.R;
import com.example.duacentes.adapters.PrinciplesLearningAdapter;
import com.example.duacentes.config.HttpsTrustManager;
import com.example.duacentes.config.general_data;
import com.example.duacentes.interfaces.iCommunicates_Fragments;
import com.example.duacentes.models.GuidelineModel;
import com.example.duacentes.models.PrincipleModel;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class SectionsearchFragment extends Fragment {

    /**
     * RequestQueue volley
     */

    private RequestQueue requestQueue;

    /**
     * Comunicación entre fragmentos
     */
    private iCommunicates_Fragments interfacecommunicates_Fragments;

    /**
     * referencias para comunicar fragments
     */
    private Activity activitys;

    /**
     * variables para mantener sesion
     */
    private SharedPreferences preferences;
    private String iduser, names, last_name, email, image, birthdate, rol, state, user_token;
    private Boolean firstfragment;

    /**
     * Encabezado
     */

    ImageView sectionsearchimagelog;

    /**
     * Sección 1
     */

    private AutoCompleteTextView auto_complete_txt_principle;
    private List<String> ItemsnamePrinciple;
    private List<PrincipleModel> ListElementsPrinciple;
    private ArrayAdapter<String> adapterItemsPrinciple;
    private PrincipleModel principleModel;

    /**
     * Sección 2
     */

    private AutoCompleteTextView auto_complete_txt_guideline;
    private List<String> ItemsnameGuideline;
    private List<GuidelineModel> ListElementsGuideline;
    private ArrayAdapter<String> adapterItemsGuideline;
    private GuidelineModel guidelineModel;



    /**
     * Sección 3
     */

    private MaterialButton btngotoresource;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_sectionsearch, container, false);

        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getActivity() != null) {
            this.init(view);
            this.sessionuser();

            this.getprinciples();

            auto_complete_txt_principle.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    guidelineModel = null;
                    auto_complete_txt_guideline.setText("");
                    auto_complete_txt_guideline.setThreshold(1);
                    getprinciples();
                }
            });


            btngotoresource.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(principleModel != null){
                        if(guidelineModel != null){
                            interfacecommunicates_Fragments.SendISGuidelineModel(guidelineModel, principleModel);

                            principleModel = null;
                            guidelineModel = null;

                            auto_complete_txt_principle.setText("");
                            auto_complete_txt_principle.setThreshold(1);

                            auto_complete_txt_guideline.setText("");
                            auto_complete_txt_guideline.setThreshold(1);


                        } else {
                            warningMessage("Por favor, seleccione una pauta");
                        }
                    }
                    else{
                        warningMessage("Por favor, seleccione un principio");
                    }


                }
            });

        }


    }

    /**
     * inicialización de componentes
     */

    private void init(View view) {

        preferences = this.getActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        sectionsearchimagelog = (ImageView) view.findViewById(R.id.sectionsearchimagelog);

        // Sección 1
        auto_complete_txt_principle = (AutoCompleteTextView) view.findViewById(R.id.auto_complete_txt_principle);

        // Sección 2
        auto_complete_txt_guideline = (AutoCompleteTextView) view.findViewById(R.id.auto_complete_txt_guideline);
        auto_complete_txt_guideline.setEnabled(false);

        // Sección 3
        btngotoresource = (MaterialButton) view.findViewById(R.id.btngotoresource);
      //  btngotoresource.setEnabled(false);





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
                        /*  proDialogLearning.dismiss();*/

                        ListElementsPrinciple = new ArrayList<>();

                        ItemsnamePrinciple = new ArrayList<>();

                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            ItemsnamePrinciple.add(object.get("name").toString());


                            ListElementsPrinciple.add(new PrincipleModel(
                                    object.getInt("idprinciple"),
                                    object.get("name").toString(),
                                    object.get("description").toString(),
                                    general_data.URLIMAG + object.get("image").toString().replace("\\", ""),
                                    object.get("creationdate").toString(),
                                    object.get("updatedate").toString(),
                                    object.getBoolean("state")));

                        }

                        adapterItemsPrinciple = new ArrayAdapter<String>(getActivity(), R.layout.list_item, ItemsnamePrinciple);
                      //  auto_complete_txt_principle.setText(ItemsnamePrinciple.get(0));
                        auto_complete_txt_principle.setAdapter(adapterItemsPrinciple);
                        auto_complete_txt_principle.setThreshold(1);

                       //


                        auto_complete_txt_principle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String item = adapterView.getItemAtPosition(i).toString();
                                getguidelinebyprinciple(ListElementsPrinciple.get(i).getIdprinciple());
                                principleModel = ListElementsPrinciple.get(i);
                               // Toast.makeText(getActivity(), "Item "+ item, Toast.LENGTH_SHORT).show();
                            }
                        });


                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                        //  proDialogLearning.dismiss();
                    }
                } catch (
                        JSONException e) {
                    e.printStackTrace();
                    //  proDialogLearning.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", String.valueOf(error));
                //    proDialogLearning.dismiss();
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

    private void getguidelinebyprinciple(int idprinciple) {

       // Log.d("ID PRINCIPIO: ", String.valueOf(idprinciple) );

        HttpsTrustManager.allowAllSSL();

        /**
         * API
         */
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, general_data.URL + "guidelines/getguidelinesprinciplebyidprinciple?idprinciple=" + idprinciple,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("flag")) {
                        /*  proDialogLearning.dismiss();*/

                        ItemsnameGuideline = new ArrayList<>();
                        ListElementsGuideline = new ArrayList<>();

                        JSONArray jsonArray = response.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            ItemsnameGuideline.add(object.get("name").toString());

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

                        if(ItemsnameGuideline.size() > 0){
                            auto_complete_txt_guideline.setEnabled(true);
                            adapterItemsGuideline = new ArrayAdapter<String>(getActivity(), R.layout.list_item, ItemsnameGuideline);
                            auto_complete_txt_guideline.setAdapter(adapterItemsGuideline);
                            auto_complete_txt_guideline.setThreshold(1);
                        }


                        auto_complete_txt_guideline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                String item = adapterView.getItemAtPosition(i).toString();
                                guidelineModel = ListElementsGuideline.get(i);
                                if(item != null || item != ""){
                                 //   btngotoresource.setEnabled(true);
                                }
                            }
                        });


                    } else {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                        //  proDialogLearning.dismiss();
                    }
                } catch (
                        JSONException e) {
                    e.printStackTrace();
                    //  proDialogLearning.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", String.valueOf(error));
                //    proDialogLearning.dismiss();
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

    public void successMessage(String message) {
        new SweetAlertDialog(getActivity(),
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Excelente!")
                .setContentText(message).show();
    }

    public void errorMessage(String message) {
        new SweetAlertDialog(getActivity(),
                SweetAlertDialog.ERROR_TYPE).setTitleText("Oops...").setContentText(message).show();
    }

    public void warningMessage(String message) {
        new SweetAlertDialog(getActivity(),
                SweetAlertDialog.WARNING_TYPE).setTitleText("Notificación del Sistema")
                .setContentText(message).setConfirmText("Ok").show();
    }

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