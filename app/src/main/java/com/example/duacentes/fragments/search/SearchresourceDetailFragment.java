package com.example.duacentes.fragments.search;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.duacentes.models.ToolModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;


public class SearchresourceDetailFragment extends Fragment {

    /**
     * Modelo Herramientas
     */
    private ToolModel toolModel;

    /**
     * Bloque 1
     */

    private ShapeableImageView imageresearchresource;


    /**
     * Bloque 2
     */

    private TextView namesearchresource;

    /**
     * Bloque 3
     */

    private TextView descripsearchresource;

    /**
     * Bloque 4
     */

    private TextView linksearchresource;
    private MaterialButton btngotosearchresource;

    private int[] backgroundsbuttons = {
            R.drawable.buttonlearningrepresentation,
            R.drawable.buttonlearningacex,
            R.drawable.buttonlearningengagement,
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_searchresource_detail, container, false);
        this.init(inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getActivity() != null) {

            Bundle object= getArguments();
            toolModel = null;

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
                        }).into(imageresearchresource);

                //seccion 2
                namesearchresource.setText(toolModel.getName());

                //seccion 3
                descripsearchresource.setText(toolModel.getDescription());

                //seccion 4
                linksearchresource.setText(Html.fromHtml("<a>" + toolModel.getUrltool() + "</a>"));

                btngotosearchresource.setBackgroundResource(backgroundsbuttons[toolModel.getIdprinciple() - 1]);


                btngotosearchresource.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(toolModel.getUrltool());
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);

                    }
                });


            }


        }
    }

    private void init(View view) {

        // Bloque 1

        imageresearchresource = (ShapeableImageView) view.findViewById(R.id.imageresearchresource);

        // Bloque 2

        namesearchresource = (TextView) view.findViewById(R.id.namesearchresource);

        // Bloque 3

        descripsearchresource = (TextView) view.findViewById(R.id.descripsearchresource);

        // Bloque 4

        linksearchresource = (TextView) view.findViewById(R.id.linksearchresource);
        btngotosearchresource = (MaterialButton) view.findViewById(R.id.btngotosearchresource);
    }
}