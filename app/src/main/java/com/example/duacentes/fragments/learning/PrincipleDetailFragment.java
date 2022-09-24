package com.example.duacentes.fragments.learning;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.duacentes.R;
import com.example.duacentes.activitys.MainActivity;
import com.example.duacentes.activitys.RegisterActivity;
import com.example.duacentes.interfaces.iCommunicates_Fragments;
import com.example.duacentes.models.PrincipleModel;
import com.google.android.material.button.MaterialButton;

import java.util.Locale;

public class PrincipleDetailFragment extends Fragment {

    /**
     * Barra de progreso de carga
     */
    private ProgressDialog proDialogLearning;

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
     * Sección 1
     */
    private TextView tittleprincipledetail;
    private ImageView imgheaderprinciple;

    private int[] backgrounds = {
            R.drawable.cardlearningrepresentation,
            R.drawable.cardlearningacex,
            R.drawable.cardlearningengagement,
    };

    /**
     * Sección 2
     */
    private TextView descriptionprincipledetail1;

    private String[] descriptiondetailbyprinciple1 = {
            "Los alumnos perciben y comprenden la información que se les presenta de diferentes maneras, ya sea por limitaciones sensoriales (visuales o auditivas), problemas de aprendizaje (dislexia), diferencias de idioma o culturales, entre otras. En otros casos, el procesamiento más rápido o eficiente de la información puede ser una cuestión de facilidad o preferencia percibida, ya sea que la información se presente a través de canales auditivos, visuales o impresos. Por eso, es importante que los docentes brinden opciones para adquirir o procesar información que permita el aprendizaje. <br> <br>" +
                    "Para este primer componente del DUA, los docentes activan la <b>red de reconocimiento</b> del cerebro mediante la identificación de hechos, ideas y conceptos clave, principalmente asociados con el <b>“contenido”</b> del aprendizaje. Los docentes usan el conocimiento previo y los estilos de aprendizaje de los estudiantes para informar el contenido presentado en una variedad de formas apropiadas para las fortalezas de los estudiantes y para apoyar las conexiones entre la información nueva y la experiencia previa.",

            "La premisa del DUA es que no existe una mejor manera de actuar y expresarlo para todos los alumnos. Existen grandes diferencias en la forma en que procesan e interactúan con la información en situaciones de aprendizaje y expresan lo que están aprendiendo, lo que puede deberse a sus características o preferencias personales, o barreras derivadas de un conocimiento insuficiente del idioma, problemas de movimiento, limitaciones de memoria, etc <br> <br>" +
                    "El segundo componente, medios múltiples de expresión, implica la activación de la <b>red estratégica</b> del cerebro asociada principalmente con el aprendizaje del “cómo”. Es útil para planificar y ejecutar acciones y respuestas a la información proporcionada. Al brindarles a los estudiantes múltiples formas de demostrar lo que saben, los maestros les brindan opciones para comunicar su comprensión y dominio de las tareas académicas.",

            "El afecto representa un elemento crucial para el aprendizaje, <b> y los alumnos difieren notablemente en las formas en que pueden estar comprometidos o motivados para aprender. </b> Esta diversidad en la motivación puede tener su origen en factores de tipo neurológico, cultural, interés personal, conocimientos o experiencias, previas. <br> <br> " +
                    "Así como algunas personas están motivadas para trabajar en equipo, otras prefieren trabajar individualmente. En algunos casos, les inspira cosas nuevas; en otros, les genera incertidumbre o inseguridades y prefieren la rutina. Como señala el <b>CAST</b>, no existe un método único que capte el interés o el compromiso de todos los estudiantes en todas las situaciones. Para hacer frente a esta variabilidad en los entornos educativos, es importante ofrecer opciones que permitan diferentes formas de participar en el aprendizaje. <br> <br>" +
                    "El tercer componente, medios múltiples de implicación, implica el “por qué” del aprendizaje y está relacionado con la red emocional del cerebro. La red ayuda a dar sentido a porqué los alumnos actúan sobre la información de cierta manera. Para involucrar a los estudiantes, los maestros conectan el aprendizaje con la vida real fomentando sus intereses y motivándolos. Este componente también incluye interacciones sociales con compañeros y el entorno social que los alumnos disfrutan mientras estudian."
            };

    /**
     * Sección 3
     */

    private ImageView imgprincipledetail;

    private int[] imgprinciplespautas = {
            R.drawable.principiorepresentacion,
            R.drawable.principioaccionexpresion,
            R.drawable.principioimplicacion,
    };

    /**
     * Sección 4
     */

    private TextView descriptionprincipledetail2;

    private String[] descriptiondetailbyprinciple2 = {
            "Los medios de representación se enfocan en el <b>“¿QUÉ se aprende?”</b>, y para aquello, se tiene un conjunto de estrategias que son tres pautas que se pueden utilizar en la práctica docente con la finalidad de lograr currículos accesibles.",

            "Los medios de acción y expresión se enfocan en el <b>“¿CÓMO se aprende?”</b>, para llevar a cabo esta área de acción y expresión, de la misma forma que el primer bloque del DUA, se tiene un conjunto de estrategias que son tres pautas.",

            "Los medios de implicación se enfocan en el <b>“¿POR QUÉ se aprende?”</b>. De igual forma que el primer y segundo componente del DUA, contiene un conjunto de estrategias que son tres pautas."
    };

    /**
     * Sección 5
     */

    private TextView  textvbuttonprincipledetail;

    private int[] backgroundsbuttons = {
            R.drawable.buttonlearningrepresentation,
            R.drawable.buttonlearningacex,
            R.drawable.buttonlearningengagement,
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_principle_detail, container, false);
        this.init(inflate);

        proDialogLearning = ProgressDialog.show(getActivity(), "Cargando...", "Espere un momento...", true);

        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle object= getArguments();
        principleModel = null;
        if(object!=null){
            principleModel = (PrincipleModel) object.getSerializable("object");

            //sección 1

            tittleprincipledetail.setText(principleModel.getName());


            Glide.with(this).load(principleModel.getImage().replace('\\', '/'))
                    .placeholder(R.drawable.progress_animation)
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .error(R.drawable.try_later)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    }).into(imgheaderprinciple);

            //sección 2

            descriptionprincipledetail1.setText(Html.fromHtml(descriptiondetailbyprinciple1[principleModel.getIdprinciple() -1]));

            //sección 3

            imgprincipledetail.setBackgroundResource(imgprinciplespautas[principleModel.getIdprinciple() - 1]);

            //sección 4

            descriptionprincipledetail2.setText(Html.fromHtml(descriptiondetailbyprinciple2[principleModel.getIdprinciple() -1]));


            textvbuttonprincipledetail.setBackgroundResource(backgroundsbuttons[principleModel.getIdprinciple() - 1]);

            textvbuttonprincipledetail.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    interfacecommunicates_Fragments.SendDetailPrincipleforGuidelineModel(principleModel);
                }
            });


        }
        proDialogLearning.dismiss();
    }


    private void init(View view) {

        /**
         * Sección 1
         */

        tittleprincipledetail = (TextView) view.findViewById(R.id.tittleprincipledetail);
        imgheaderprinciple = (ImageView) view.findViewById(R.id.imgheaderprinciple);

        /**
         * Sección 2
         */

        descriptionprincipledetail1 = (TextView) view.findViewById(R.id.descriptionprincipledetail1);

        /**
         * Sección 3
         */

        imgprincipledetail = (ImageView)  view.findViewById(R.id.imgprincipledetail);

        /**
         * Sección 4
         */

        descriptionprincipledetail2 = (TextView) view.findViewById(R.id.descriptionprincipledetail2);

        /**
         * Seccion 5
         */

        textvbuttonprincipledetail = (TextView) view.findViewById(R.id.textvbuttonprincipledetail);
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