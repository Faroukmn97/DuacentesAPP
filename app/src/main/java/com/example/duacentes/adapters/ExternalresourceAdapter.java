package com.example.duacentes.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.duacentes.R;
import com.example.duacentes.models.ExternalresourceModel;
import com.example.duacentes.models.PrincipleModel;

import java.util.List;

public class ExternalresourceAdapter extends RecyclerView.Adapter<ExternalresourceAdapter.ExternalresourceViewHolder> implements  View.OnClickListener{
    private List<ExternalresourceModel> mData;
    private LayoutInflater mInflater;
    private Context context;
    private View.OnClickListener listener;

    public ExternalresourceAdapter(List<ExternalresourceModel> ListExternalresource, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.mData = ListExternalresource;
        this.context = context;
    }

    @Override
    public ExternalresourceAdapter.ExternalresourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.element_externalresouce, parent,false);
        view.setOnClickListener(this);
        return new ExternalresourceAdapter.ExternalresourceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExternalresourceAdapter.ExternalresourceViewHolder holder, int position) {
        holder.nameresourceexternal.setText(mData.get(position).getName());
        Glide.with(context).load(mData.get(position).getImage())
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
                }).into(holder.ExternalresourceImageview);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ExternalresourceViewHolder extends RecyclerView.ViewHolder{
        ImageView ExternalresourceImageview;
        TextView idexternalresource, nameresourceexternal, description,image,resource, creationdate,updatedate,state;


        ExternalresourceViewHolder (View itemView){
            super(itemView);
            ExternalresourceImageview = itemView.findViewById(R.id.ExternalresourceImageview);
            nameresourceexternal = itemView.findViewById(R.id.nameresourceexternal);
        }
    }
}
