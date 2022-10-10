package com.example.duacentes.fragments.internalseccion;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.duacentes.R;
import com.example.duacentes.config.TTSManager;
import com.example.duacentes.models.ToolModel;
import com.google.android.material.imageview.ShapeableImageView;

public class ISresourceDetailFragment extends Fragment {


    /**
     * Modelo Herramientas
     */
    private ToolModel toolModel;

    /**
     * Bloque 1
     */

    private ShapeableImageView imagereisresource;


    /**
     * Bloque 2
     */

    private TextView nameISresource;

    /**
     * Bloque 3
     */

    private TextView descripISresource;

    /**
     * Bloque 4
     */

    private TextView linkISresource;
    private TextView btngotoresource;

    private final int[] backgroundsbuttons = {
            R.drawable.buttonlearningrepresentation,
            R.drawable.buttonlearningacex,
            R.drawable.buttonlearningengagement,
    };

    private AppCompatButton btnvoz;
    TTSManager ttsManager = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_i_sresource_detail, container, false);
        this.init(inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (getActivity() != null) {

            ttsManager = new TTSManager();
            ttsManager.init(getActivity());

            Bundle object = getArguments();

            if (object != null) {
                toolModel = (ToolModel) object.getSerializable("object");

                //sección 1

                Glide.with(this).load(toolModel.getImage().replace('\\', '/'))
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
                        }).into(imagereisresource);

                //seccion 2
                nameISresource.setText(toolModel.getName());

                //seccion 3
                descripISresource.setText(toolModel.getDescription());

                //seccion 4
                linkISresource.setText(Html.fromHtml("<a>" + toolModel.getUrltool() + "</a>"));

                btngotoresource.setBackgroundResource(backgroundsbuttons[toolModel.getIdprinciple() - 1]);


                btngotoresource.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(toolModel.getUrltool());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                });

                btnvoz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ttsManager.initQueue(toolModel.getDescription());
                    }
                });
            }
        }

    }

    private void init(View view) {

        // Bloque 1

        imagereisresource = (ShapeableImageView) view.findViewById(R.id.imagereisresource);

        // Bloque 2

        nameISresource = (TextView) view.findViewById(R.id.nameISresource);

        // Bloque 3

        descripISresource = (TextView) view.findViewById(R.id.descripISresource);
        btnvoz = (AppCompatButton) view.findViewById(R.id.btnvoz);

        // Bloque 4

        linkISresource = (TextView) view.findViewById(R.id.linkISresource);
        btngotoresource = (TextView) view.findViewById(R.id.btngotoresource);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ttsManager.shutDown();
    }


}