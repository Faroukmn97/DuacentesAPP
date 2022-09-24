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
import com.example.duacentes.models.CheckpointModel;

import java.util.List;

public class CheckpointLearningAdapter  extends RecyclerView.Adapter<CheckpointLearningAdapter.CheckpointViewHolder> implements  View.OnClickListener{

    private List<CheckpointModel> mData;
    private LayoutInflater mInflater;
    private Context context;
    private View.OnClickListener listener;

    private int[] backgrounds = {
            R.drawable.cardlearningrepresentation,
            R.drawable.cardlearningacex,
            R.drawable.cardlearningengagement,
    };

    public CheckpointLearningAdapter(List<CheckpointModel> ListCheckpoint, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.mData = ListCheckpoint;
        this.context = context;
    }

    private int[] backgroundsbuttons = {
            R.drawable.buttonlearningrepresentation,
            R.drawable.buttonlearningacex,
            R.drawable.buttonlearningengagement,
    };

    @Override
    public CheckpointLearningAdapter.CheckpointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.element_list_checkpoint, parent,false);
        view.setOnClickListener(this);
        return new CheckpointLearningAdapter.CheckpointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckpointLearningAdapter.CheckpointViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getName());
      //  Glide.with(context).load(mData.get(position).getImage()).into(holder.ImageCheckpoint);
        holder.idcheckpoint.setText("Punto de verificación " + mData.get(position).getIdcheckpoint());
        holder.linearLayoutcheck.setBackgroundResource(backgrounds[mData.get(position).getIdprinciple() - 1]);
        holder.textvbuttoncheckpoint.setBackgroundResource(backgroundsbuttons[mData.get(position).getIdprinciple() - 1]);
        //  holder.description.setText(mData.get(position).getDescription());

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
                }).into(holder.ImageCheckpoint);
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

    public class CheckpointViewHolder extends RecyclerView.ViewHolder{
        ImageView ImageCheckpoint;
        LinearLayout linearLayoutcheck;
        TextView idcheckpoint;
        TextView textvbuttoncheckpoint;
        TextView name;


        CheckpointViewHolder (View itemView){
            super(itemView);
            linearLayoutcheck = itemView.findViewById(R.id.linearyoutimagecheck);
            ImageCheckpoint = itemView.findViewById(R.id.ImageCheckpoint);
            idcheckpoint = itemView.findViewById(R.id.idcheckpoint);
            name = itemView.findViewById(R.id.namecheckpoint);
            textvbuttoncheckpoint = itemView.findViewById(R.id.textvbuttoncheckpoint);
        }
    }
}
