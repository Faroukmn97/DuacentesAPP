package com.example.duacentes.fragments.dua;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duacentes.R;


public class DuaDetailFragment extends Fragment {

    private TextView titleduadetail;
    private TextView textduadetail1;
    private TextView textduadetail2;
    private ImageView imgduadetail;

    private String duadetail1=
            "El Diseño Universal para el Aprendizaje (DUA) se promueve como una metodología compuesta por un <b>conjunto de principios</b> para diseñar y aplicar enfoques flexibles de enseñanza y aprendizaje que aborden la diversidad de los estudiantes en el contexto del aula. Con la naturaleza diversa de las aulas contemporáneas, los profesionales de la enseñanza buscan nuevos métodos para hacer frente a los retos que plantea esta diversidad. ";

    private String duadetail2=
            "El DUA se considera un marco apropiado para diseñar <b>planes curriculares inclusivos</b> que satisfagan las necesidades educativas y las diversas necesidades de aprendizaje de los estudiantes con y sin discapacidad. Al planificar proactivamente la flexibilidad utilizando el DUA, la enseñanza y el aprendizaje se hace accesible para todos los alumnos.";


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

        textduadetail1.setText(Html.fromHtml(duadetail1));
        textduadetail2.setText(Html.fromHtml(duadetail2));

    }

    private void init(View view) {


        titleduadetail = (TextView) view.findViewById(R.id.titleduadetail);
        textduadetail1 = (TextView) view.findViewById(R.id.textduadetail1);
        textduadetail2= (TextView) view.findViewById(R.id.textduadetail2);
        imgduadetail = (ImageView) view.findViewById(R.id.imgduadetail);

    }
}