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

public class GuidelineLearningAdapter extends RecyclerView.Adapter<GuidelineLearningAdapter.GuidelineViewHolder> implements  View.OnClickListener{
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


    public GuidelineLearningAdapter(List<GuidelineModel> ListGuideline, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.mData = ListGuideline;
        this.context = context;
    }

    @Override
    public GuidelineLearningAdapter.GuidelineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.element_list_guideline, parent,false);
        view.setOnClickListener(this);
        return new GuidelineLearningAdapter.GuidelineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuidelineLearningAdapter.GuidelineViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getName());
    //    Glide.with(context).load(mData.get(position).getImage()).into(holder.ImageGuideline);
        holder.idguideline.setText("Pauta " + mData.get(position).getIdguideline());
        holder.linearyoutimageguideline.setBackgroundResource(backgrounds[mData.get(position).getIdprinciple() - 1]);
        holder.textvbuttonguideline.setBackgroundResource(backgroundsbuttons[mData.get(position).getIdprinciple() - 1]);

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
                }).into(holder.ImageGuideline);

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

    public class GuidelineViewHolder extends RecyclerView.ViewHolder{
        ImageView ImageGuideline;
        LinearLayout linearyoutimageguideline;
        TextView idguideline;
        TextView name;
        TextView textvbuttonguideline;


        GuidelineViewHolder (View itemView){
            super(itemView);
            linearyoutimageguideline = itemView.findViewById(R.id.linearyoutimageguideline);
            ImageGuideline = itemView.findViewById(R.id.ImageGuideline);
            idguideline = itemView.findViewById(R.id.idguideline);
            name = itemView.findViewById(R.id.nameguideline);
            textvbuttonguideline = itemView.findViewById(R.id.textvbuttonguideline);

        }
    }
}
