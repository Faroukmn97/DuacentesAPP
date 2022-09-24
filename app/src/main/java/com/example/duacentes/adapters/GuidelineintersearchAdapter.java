package com.example.duacentes.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.duacentes.models.GuidelineModel;

import java.util.List;

public class GuidelineintersearchAdapter extends RecyclerView.Adapter<GuidelineintersearchAdapter.GuidelineintersearchViewHolder> implements  View.OnClickListener{
    private List<GuidelineModel> mData;
    private LayoutInflater mInflater;
    private Context context;
    private View.OnClickListener listener;

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

    public GuidelineintersearchAdapter(List<GuidelineModel> ListGuideline, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.mData = ListGuideline;
        this.context = context;
    }

    @Override
    public GuidelineintersearchAdapter.GuidelineintersearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.element_intersearch_guideline, parent,false);
        view.setOnClickListener(this);
        return new GuidelineintersearchAdapter.GuidelineintersearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuidelineintersearchAdapter.GuidelineintersearchViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getName());
    //    Glide.with(context).load(mData.get(position).getImage()).into(holder.ImageintersearchGuideline);
        holder.idguideline.setText("Pauta " + mData.get(position).getIdguideline());
        holder.linearyoutImageintersearchGuideline.setBackgroundResource(backgrounds[mData.get(position).getIdprinciple() - 1]);
        holder.textvbuttonISguideline.setBackgroundResource(backgroundsbuttons[mData.get(position).getIdprinciple() - 1]);

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
                }).into(holder.ImageintersearchGuideline);
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

    public class GuidelineintersearchViewHolder extends RecyclerView.ViewHolder{
        ImageView ImageintersearchGuideline;
        LinearLayout linearyoutImageintersearchGuideline;
        TextView idguideline, name, idprinciple,textvbuttonISguideline, principle, description,image,creationdate,updatedate,state;


        GuidelineintersearchViewHolder (View itemView){
            super(itemView);
            ImageintersearchGuideline = itemView.findViewById(R.id.ImageintersearchGuideline);
            linearyoutImageintersearchGuideline = itemView.findViewById(R.id.linearyoutImageintersearchGuideline);
            idguideline = itemView.findViewById(R.id.idintersearchguideline);
            name = itemView.findViewById(R.id.nameintersearchguideline);
            textvbuttonISguideline =  itemView.findViewById(R.id.textvbuttonISguideline);
        }
    }
}

