package com.example.duacentes.fragments.learning;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.duacentes.interfaces.iCommunicates_Fragments;
import com.example.duacentes.models.GuidelineModel;
import com.example.duacentes.models.PrincipleModel;

public class GuidelineDetailFragment extends Fragment {

    /**
     * Comunicación entre fragmentos
     */
    private iCommunicates_Fragments interfacecommunicates_Fragments;

    /**
     * referencias para comunicar fragments
     */
    private Activity activitys;


    /**
     * Modelo pautas
     */
    private GuidelineModel guidelineModel;

    /**
     * Modelo principio
     */
    private PrincipleModel principleModel;

    /**
     * Barra de progreso de carga
     */
    private ProgressDialog proDialogLearning;

    /**
     * Sección 1
     */
    private TextView tittleguidelinedetail;
    private ImageView imgheaderguideline;
    private LinearLayout linearyoutimageheaderguideline;

    private int[] backgrounds = {
            R.drawable.cardlearningrepresentation,
            R.drawable.cardlearningacex,
            R.drawable.cardlearningengagement,
    };

    /**
     * Sección 2
     */

    private TextView descriptionguidelinedetail1;

    private String[] descriptiondetailbyguidelinedetail1 = {
            "Si los estudiantes no pueden percibir la información, no hay aprendizaje. Para evitar que esto se convierta en un obstáculo, es importante lograr que todos los estudiantes perciban la información clave, para ello se pueden buscar formas alternativas que permitan presentar la información. Es decir, se trata de brindar la misma información a través de diferentes medios (p. ej., visual, auditivo, táctil o audiovisual).",

            "Las interacciones con varios sistemas de representación (verbales y no verbales) varían de estudiante a estudiante. Por ejemplo, dibujos o imágenes que tienen el mismo significado para algunos estudiantes pueden tener significados completamente diferentes para otros estudiantes de diferentes culturas y antecedentes familiares. Lo mismo sucede cuando se utilizan símbolos, gráficos o palabras específicas. Si la información se presenta utilizando únicamente este medio, será inaccesible para aquellos que no entienden las palabras o las representaciones gráficas. Por lo tanto, si se utilizan de manera complementaria, los <b>sistemas de presentación alternativos</b>, como las palabras y las imágenes, mejorarán la claridad y la comprensión de todos los alumnos.",

            "El propósito de la educación no es hacer accesible la información, sino enseñar a los estudiantes a convertir la información a la que tienen acceso en conocimiento, lo que se logra a través de un proceso activo. Para ser útil, este conocimiento debe estar disponible para los estudiantes para que puedan usarlo en la toma de decisiones o como base para adquirir nueva información.",

            "Los libros de texto o de trabajo impresos ofrecen opciones de interacción muy limitadas. Por ejemplo, alguien interactúa pasando páginas con los dedos o escribiendo en el espacio establecido a tal efecto. <br><br>" +
                    "De la misma manera, muchos programas educativos multimedia ofrecen medios limitados de interacción. Así que podemos usar un joystick, un ratón o un teclado. Esto puede ser un obstáculo para algunos estudiantes (especialmente aquellos con discapacidades motoras o visuales o discapacidades de escritura). ",

            "Ningún medio de expresión es igualmente adecuado para todos los estudiantes o todos los tipos de comunicación. Por el contrario, algunas formas de comunicación parecen inapropiadas para ciertos tipos de expresiones y ciertos tipos de estudiantes.",

            "La función ejecutiva es el nivel más alto de la capacidad humana y es esencial para el dominio de la ejecución. Estas habilidades se correlacionan con la actividad cerebral en la corteza prefrontal, lo que les permite a los humanos superar los impulsos o las respuestas a corto plazo; en su lugar, actúa estableciendo metas u objetivos a largo plazo, un plan para alcanzar esas metas, monitoreando su progreso y modificándolos según sea necesario.",

            "La información a la que no se atiende o a la que no se presta atención, la que no supone una actividad cognitiva del estudiante, es, de hecho, inaccesible y lo es tanto en el momento presente como en el futuro, porque la información que pudiera ser relevante pasa desapercibida y no se procesa. Por esta razón, la mayoría de las actividades de enseñanza se dedican a captar la atención de los estudiantes y lograr el compromiso de los estudiantes. Estos hacen una diferencia significativa para captar su atención y despertar su interés. Estas preferencias, incluso dentro del mismo estudiante, pueden variar con el tiempo y el contexto.",

            "Muchos tipos de aprendizaje, especialmente el aprendizaje de habilidades y estrategias, requieren atención y esfuerzo sostenidos. Cuando los estudiantes están motivados, pueden regular su atención y sus partes emocionales para mantener el esfuerzo y la concentración necesarios para aprender. Sin embargo, la capacidad de autorregulación varía de persona a persona. <br><br>" +
                    "Estas diferencias también son evidentes en su motivación inicial, capacidad de autorregulación y susceptibilidad a las perturbaciones ambientales. Un objetivo educativo clave es desarrollar habilidades de autorregulación y autodeterminación personal para garantizar oportunidades de aprendizaje para todos.",

            "Si bien es importante cuidar los aspectos extrínsecos del aprendizaje para promover una buena motivación e implicación (ver pautas 7 y 8), también es importante <b>desarrollar las habilidades intrínsecas de los estudiantes para regular sus emociones y motivación.</b>"
    };

    /**
     * Sección 3
     */

    private TextView descriptionguidelinedetail2;

    private String[] descriptiondetailbyguidelinedetail2 = {
            "Esta pauta se puede concretar en acciones más específicas para su implementación en la práctica: son los puntos de verificación que se describen seguidamente.",

            "Esta pauta se puede concretar en acciones más específicas para su implementación en la práctica: son los puntos de verificación que se describen seguidamente.",

            "Se debe brindar a los estudiantes la ayuda y el apoyo necesarios para garantizar que accedan y procesen la información de manera significativa. <br><br>" +
                    "Esta pauta se puede concretar en acciones más específicas para su implementación en la práctica: son los puntos de verificación que se describen seguidamente.",

            "Es importante que los materiales del curso estén diseñados para ser compatibles con las tecnologías de asistencia comunes a través de las cuales los estudiantes que necesitan usarlos puedan expresar lo que saben. Por ejemplo, deben permitir la interacción con interruptores activados por voz, teclados extendidos y otras tecnologías de asistencia. <br><br>" +
                    "Esta pauta se puede concretar en acciones más específicas para su implementación en la práctica: son los puntos de verificación que se describen seguidamente.",

            "Esta pauta se puede concretar en acciones más específicas para su implementación en la práctica: son los puntos de verificación que se describen seguidamente.",

            "Es probable que los docentes y los entornos que abordan explícitamente la autorregulación tengan más éxito en la aplicación de los principios DUA a través del modelado y a través de una variedad de métodos para que los estudiantes logren estas competencias. Como con cualquier aprendizaje, las diferencias individuales son más comunes que la consistencia. Por esta razón, es conveniente brindar alternativas adecuadas para ayudar a los estudiantes con experiencia previa y habilidades diferentes a manejar de manera efectiva la forma en que se involucran en su propio aprendizaje. <br><br>" +
                    "Esta pauta se puede concretar en acciones más específicas para su implementación en la práctica: son los puntos de verificación que se describen seguidamente.",

            "A medida que los estudiantes atraviesan cambios biológicos y se convierten en adolescentes o adultos, los intereses cambian a medida que los estudiantes desarrollan y adquieren nuevos conocimientos y habilidades. Por lo tanto, es importante <b>disponer de métodos alternativos para captar intereses y estrategias</b> para hacer frente a las diferencias intra e interindividuales. <br><br>" +
                    "Esta pauta se puede concretar en acciones más específicas para su implementación en la práctica: son los puntos de verificación que se describen seguidamente.",

            "Esta pauta se puede concretar en acciones más específicas para su implementación en la práctica: son los puntos de verificación que se describen seguidamente.",

            "A menudo, el desarrollo de esta habilidad no se considera explícitamente en el aula, sino como parte de un plan de estudios implícito que a menudo no está disponible o es invisible para muchos estudiantes. Como con cualquier aprendizaje, las diferencias individuales son más comunes que la consistencia. Por esta razón, es recomendable brindar alternativas adecuadas para ayudar a los estudiantes con experiencia previa y habilidades variadas a administrar cómo se involucran en su propio aprendizaje. <br><br>" +
                    "Esta pauta se puede concretar en acciones más específicas para su implementación en la práctica: son los puntos de verificación que se describen seguidamente."
    };

    /**
     * Sección 4
     */

    private TextView  textvbuttonguidelinedetail;

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

        View inflate = inflater.inflate(R.layout.fragment_guideline_detail, container, false);
        this.init(inflate);

        proDialogLearning = ProgressDialog.show(getActivity(), "Cargando...", "Espere un momento...", true);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle object= getArguments();
        principleModel = null;
        guidelineModel = null;
        if(object!=null){
            guidelineModel = (GuidelineModel) object.getSerializable("object");
            principleModel = (PrincipleModel) object.getSerializable("object2");

            //sección 1

            linearyoutimageheaderguideline.setBackgroundResource(backgrounds[principleModel.getIdprinciple() - 1]);

            tittleguidelinedetail.setText(guidelineModel.getName());

            Glide.with(this).load(guidelineModel.getImage().replace('\\', '/'))
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
                    }).into(imgheaderguideline);

            //sección 2

            descriptionguidelinedetail1.setText(Html.fromHtml(descriptiondetailbyguidelinedetail1[principleModel.getIdprinciple() -1]));

            //sección 3

            descriptionguidelinedetail2.setText(Html.fromHtml(descriptiondetailbyguidelinedetail2[principleModel.getIdprinciple() -1]));

            // sección 4

            textvbuttonguidelinedetail.setBackgroundResource(backgroundsbuttons[principleModel.getIdprinciple() - 1]);

            textvbuttonguidelinedetail.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    interfacecommunicates_Fragments.SendDetailGuidelineforCheckpoints(guidelineModel, principleModel);
                }
            });
        }

        proDialogLearning.dismiss();
    }

    private void init(View view) {

        /**
         * Sección 1
         */

        tittleguidelinedetail = (TextView) view.findViewById(R.id.tittleguidelinedetail);
        imgheaderguideline = (ImageView) view.findViewById(R.id.imgheaderguideline);
        linearyoutimageheaderguideline = (LinearLayout) view.findViewById(R.id.linearyoutimageheaderguideline);

        /**
         * Sección 2
         */

        descriptionguidelinedetail1 = (TextView) view.findViewById(R.id.descriptionguidelinedetail1);

        /**
         * 3
         */

        descriptionguidelinedetail2 = (TextView) view.findViewById(R.id.descriptionguidelinedetail2);

        /**
         * Sección 4
         */

        textvbuttonguidelinedetail = (TextView) view.findViewById(R.id.textvbuttonguidelinedetail);
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