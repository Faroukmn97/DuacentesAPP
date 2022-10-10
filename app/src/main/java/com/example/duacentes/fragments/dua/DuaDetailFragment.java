package com.example.duacentes.fragments.dua;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duacentes.R;
import com.example.duacentes.config.TTSManager;

import java.util.Locale;


public class DuaDetailFragment extends Fragment {

    private TextView textduadetail1;
    private TextView textduadetail2;
    private AppCompatButton btnvoz;
    private AppCompatButton btnvoz2;
    TTSManager ttsManager = null;

    private String duadetail1=
            "El Diseño Universal para el Aprendizaje (DUA) se promueve como una metodología compuesta por un <b>conjunto de principios</b> para diseñar y aplicar enfoques flexibles de enseñanza y aprendizaje que aborden la diversidad de los estudiantes en el contexto del aula. Con la naturaleza diversa de las aulas contemporáneas, los profesionales de la enseñanza buscan nuevos métodos para hacer frente a los retos que plantea esta diversidad. ";

    private String duadetail1voz=
            "El Diseño Universal para el Aprendizaje (DUA) se promueve como una metodología compuesta por un conjunto de principios para diseñar y aplicar enfoques flexibles de enseñanza y aprendizaje que aborden la diversidad de los estudiantes en el contexto del aula. Con la naturaleza diversa de las aulas contemporáneas, los profesionales de la enseñanza buscan nuevos métodos para hacer frente a los retos que plantea esta diversidad. ";

    private String duadetail2=
            "El DUA se considera un marco apropiado para diseñar <b>planes curriculares inclusivos</b> que satisfagan las necesidades educativas y las diversas necesidades de aprendizaje de los estudiantes con y sin discapacidad. Al planificar proactivamente la flexibilidad utilizando el DUA, la enseñanza y el aprendizaje se hace accesible para todos los alumnos.";
    private String duadetail2voz=
            "El DUA se considera un marco apropiado para diseñar planes curriculares inclusivos que satisfagan las necesidades educativas y las diversas necesidades de aprendizaje de los estudiantes con y sin discapacidad. Al planificar proactivamente la flexibilidad utilizando el DUA, la enseñanza y el aprendizaje se hace accesible para todos los alumnos.";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_dua_detail, container, false);
        this.init(inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

        ttsManager = new TTSManager();
        ttsManager.init(getActivity());

        textduadetail1.setText(Html.fromHtml(duadetail1));
        textduadetail2.setText(Html.fromHtml(duadetail2));

        btnvoz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ttsManager.initQueue(duadetail1voz.toString());
            }
        });

        btnvoz2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ttsManager.initQueue(duadetail2voz.toString());
            }
        });

    }

    private void init(View view) {


        TextView titleduadetail = (TextView) view.findViewById(R.id.titleduadetail);
        textduadetail1 = (TextView) view.findViewById(R.id.textduadetail1);
        textduadetail2= (TextView) view.findViewById(R.id.textduadetail2);
        ImageView imgduadetail = (ImageView) view.findViewById(R.id.imgduadetail);
        btnvoz = (AppCompatButton) view.findViewById(R.id.btnvoz);
        btnvoz2 = (AppCompatButton) view.findViewById(R.id.btnvoz2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ttsManager.shutDown();
    }
}