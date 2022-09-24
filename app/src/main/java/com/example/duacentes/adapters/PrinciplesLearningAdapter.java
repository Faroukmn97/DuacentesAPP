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
import com.example.duacentes.models.PrincipleModel;
import com.example.duacentes.R;

import java.util.List;

public class PrinciplesLearningAdapter extends RecyclerView.Adapter<PrinciplesLearningAdapter.PrincipleViewHolder> implements  View.OnClickListener{
    private List<PrincipleModel> mData;
    private LayoutInflater mInflater;
    private Context context;
    private View.OnClickListener listener;
    private String[] tipeprinciple = {"¿QUÉ se aprende?","¿CÓMO se aprende?","¿POR QUÉ se aprende?"};

    private int[] backgroundsbuttons = {
            R.drawable.buttonlearningrepresentation,
            R.drawable.buttonlearningacex,
            R.drawable.buttonlearningengagement,
    };

    public PrinciplesLearningAdapter(List<PrincipleModel> ListPrinciple, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.mData = ListPrinciple;
        this.context = context;
    }

    @Override
    public PrincipleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.element_list_principle, parent,false);
        view.setOnClickListener(this);
        return new PrincipleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrinciplesLearningAdapter.PrincipleViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getName());
//        holder.idprinciple.setText("Principio " + mData.get(position).getIdprinciple());
        holder.tipeprinciple.setText(tipeprinciple[position]);
      //  Glide.with(context).load(mData.get(position).getImage()).into(holder.ImagePrinciple);
        holder.textvbuttonprinciple.setBackgroundResource(backgroundsbuttons[mData.get(position).getIdprinciple() - 1]);

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
                }).into(holder.ImagePrinciple);
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

    public class PrincipleViewHolder extends RecyclerView.ViewHolder{
        ImageView ImagePrinciple;
        TextView tipeprinciple;
        TextView textvbuttonprinciple;
        TextView name;


        PrincipleViewHolder (View itemView){
            super(itemView);
            ImagePrinciple = itemView.findViewById(R.id.ImagePrinciple);
          //  idprinciple = itemView.findViewById(R.id.idprinciple);
            textvbuttonprinciple = itemView.findViewById(R.id.textvbuttonprinciple);
            tipeprinciple =  itemView.findViewById(R.id.tipeprinciple);
            name = itemView.findViewById(R.id.nameprinciple);
        }
    }
}
