package com.example.duacentes.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.example.duacentes.config.HttpsTrustManager;
import com.example.duacentes.config.general_data;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutFragment extends Fragment {

    CircleImageView imgdeveloper;
    CircleImageView imgdirector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_about, container, false);
        this.init(inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        HttpsTrustManager.allowAllSSL();
        Log.d("developer", general_data.URLIMAG + "duacentes/static/images/personas/desarrollador_web.jpg");
        Log.d("director", general_data.URLIMAG + "duacentes/static/images/personas/orlandoerazo.png");

        Glide.with(this).load(general_data.URLIMAG + "duacentes/static/images/personas/desarrollador_web.jpg".toString().replace("\\", ""))
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
                }).into(imgdeveloper);

        Glide.with(this).load(general_data.URLIMAG + "duacentes/static/images/personas/orlandoerazo.png".toString().replace("\\", ""))
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
                }).into(imgdirector);
    }

    private void init(View view) {
        // bloque 1
        imgdeveloper = (CircleImageView) view.findViewById(R.id.imgdeveloper);
        imgdirector = (CircleImageView) view.findViewById(R.id.imgdirector);

    }


}