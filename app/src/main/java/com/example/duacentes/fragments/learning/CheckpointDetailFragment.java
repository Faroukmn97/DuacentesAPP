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
import com.example.duacentes.fragments.LearningFragment;
import com.example.duacentes.interfaces.iCommunicates_Fragments;
import com.example.duacentes.models.CheckpointModel;
import com.example.duacentes.models.GuidelineModel;
import com.example.duacentes.models.PrincipleModel;


public class CheckpointDetailFragment extends Fragment {

    /**
     * Comunicación entre fragmentos
     */
    private iCommunicates_Fragments interfacecommunicates_Fragments;

    /**
     * Barra de progreso de carga
     */
    private ProgressDialog proDialogLearning;

    /**
     * Sección 1
     */

    private TextView tittlecheckpointdetail;
    private ImageView imgheadercheckpoint;
    private LinearLayout linearyoutimageheadercheckpoint;

    private int[] backgrounds = {
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
     * Sección 2
     */

    private TextView descriptioncheckpointdetail;

    private String[] descriptiondetailbycheckpoint = {
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12"
    };

    /**
     * Boton de regresar a pautas
     */
    private TextView textvbuttonguidelinecheckdetail;

    /**
     * Boton de regresar a principios
     */

    private TextView textvbuttonprinciplecheckdetail;

    /**
     * Variable principio
     */
    private PrincipleModel principleModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_checkpoint_detail, container, false);

        this.init(inflate);
        proDialogLearning = ProgressDialog.show(getActivity(), "Cargando...", "Espere un momento...", true);

        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle object= getArguments();
        /**
         * Modelo puntos de verificación
         */
        CheckpointModel checkpointModel = null;

        if(object!=null){
            checkpointModel = (CheckpointModel) object.getSerializable("object");
            principleModel = (PrincipleModel)  object.getSerializable("object2");

            //sección 1

            linearyoutimageheadercheckpoint.setBackgroundResource(backgrounds[checkpointModel.getIdprinciple() - 1]);

            tittlecheckpointdetail.setText(checkpointModel.getName());

            Glide.with(this).load(checkpointModel.getImage().replace('\\', '/'))
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
                    }).into(imgheadercheckpoint);

            //sección 2

            descriptioncheckpointdetail.setText(Html.fromHtml(checkpointModel.getDescription()));

            textvbuttonguidelinecheckdetail = view.findViewById(R.id.textvbuttonguidelinecheckdetail);

            textvbuttonprinciplecheckdetail = view.findViewById(R.id.textvbuttonprinciplecheckdetail);

            textvbuttonguidelinecheckdetail.setBackgroundResource(backgroundsbuttons[checkpointModel.getIdprinciple() - 1]);



            textvbuttonguidelinecheckdetail.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    interfacecommunicates_Fragments.SendDetailPrincipleforGuidelineModel(principleModel);
                }
            });

            textvbuttonprinciplecheckdetail.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in,0,0,R.anim.slide_out)
                            .replace(R.id.contentf, new LearningFragment()).addToBackStack(null).commit();
                }
            });

        }

        proDialogLearning.dismiss();
    }

    private void init(View view) {
        /**
         * Sección 1
         */

        tittlecheckpointdetail = (TextView) view.findViewById(R.id.tittlecheckpointdetail);
        imgheadercheckpoint = (ImageView) view.findViewById(R.id.imgheadercheckpoint);
        linearyoutimageheadercheckpoint = (LinearLayout) view.findViewById(R.id.linearyoutimageheadercheckpoint);

        /**
         * Sección 2
         */

        descriptioncheckpointdetail = (TextView) view.findViewById(R.id.descriptioncheckpointdetail);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            /**
             * referencias para comunicar fragments
             */
            Activity activitys = (Activity) context;
            interfacecommunicates_Fragments = (iCommunicates_Fragments) activitys;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}