package com.example.duacentes.fragments.dua;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duacentes.R;
import com.example.duacentes.config.TTSManager;
import com.example.duacentes.fragments.HomeFragment;
import com.example.duacentes.fragments.LearningFragment;
import com.google.android.material.button.MaterialButton;


public class PrincipleAllFragment extends Fragment {

    /**
     * variables para mantener sesion
     */
    private SharedPreferences preferences;
    private String iduser, names, last_name, email, image, birthdate, rol, state, user_token;

    /**
     * Progreso
     */


    private TextView textprincipleall1;
    private MaterialButton textvbuttonprincipleall;

    private String principleall1 =
            "El modelo DUA se basa en el conocimiento de cómo funciona el cerebro. De acuerdo con el <b>CAST</b>, el cerebro usa tres redes principales. La primera red es de reconocimiento. La segunda red es de emoción y estrategia. La tercera red es de absorber y usar información. Usando este marco, los docentes desarrollan un plan para formar aulas accesibles y flexibles. Cada red cerebral está asociada con un componente clave del DUA como se detalla en las demás secciones.";

    private String principleall1voz =
            "El modelo DUA se basa en el conocimiento de cómo funciona el cerebro. De acuerdo con el CAST, el cerebro usa tres redes principales. La primera red es de reconocimiento. La segunda red es de emoción y estrategia. La tercera red es de absorber y usar información. Usando este marco, los docentes desarrollan un plan para formar aulas accesibles y flexibles. Cada red cerebral está asociada con un componente clave del DUA como se detalla en las demás secciones.";
    public PrincipleAllFragment() {
    }

    private AppCompatButton btnvoz;
    TTSManager ttsManager = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_principle_all, container, false);
        this.init(inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ttsManager = new TTSManager();
        ttsManager.init(getActivity());

        textprincipleall1.setText(Html.fromHtml(principleall1));

        textvbuttonprincipleall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in, 0, 0, R.anim.slide_out)
                        .replace(R.id.contentf, new LearningFragment()).addToBackStack(null).commit();
            }
        });

        btnvoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ttsManager.initQueue(principleall1voz.toString());
            }
        });

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

        TextView titleprincipleall = (TextView) view.findViewById(R.id.titleprincipleall);
        textprincipleall1 = (TextView) view.findViewById(R.id.textprincipleall1);
        ImageView imgprincipleall = (ImageView) view.findViewById(R.id.imgprincipleall);
        textvbuttonprincipleall = (MaterialButton) view.findViewById(R.id.textvbuttonprincipleall);
        btnvoz = (AppCompatButton) view.findViewById(R.id.btnvoz);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ttsManager.shutDown();
    }
}