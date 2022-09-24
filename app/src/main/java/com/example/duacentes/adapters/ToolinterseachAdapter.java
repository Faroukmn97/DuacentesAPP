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
import com.example.duacentes.models.ToolModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ToolinterseachAdapter extends RecyclerView.Adapter<ToolinterseachAdapter.ToolinterseachViewHolder> implements  View.OnClickListener{
    private List<ToolModel> mData;
    private LayoutInflater mInflater;
    private Context context;
    private View.OnClickListener listener;

    private int[] backgroundsbuttons = {
            R.drawable.buttonlearningrepresentation,
            R.drawable.buttonlearningacex,
            R.drawable.buttonlearningengagement,
    };

    public ToolinterseachAdapter(List<ToolModel> ListElementsISTool, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.mData = ListElementsISTool;
        this.context = context;
    }

    @Override
    public ToolinterseachAdapter.ToolinterseachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.element_interseach_resource, parent,false);
        view.setOnClickListener(this);
        return new ToolinterseachAdapter.ToolinterseachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToolinterseachAdapter.ToolinterseachViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getName());
    //    Glide.with(context).load(mData.get(position).getImage()).into(holder.ISToolImageView);
     //   holder.description.setText(mData.get(position).getDescription());
     //   holder.urltool.setText(mData.get(position).getUrltool());
     //   holder.textvbuttonISresource.setBackgroundResource(backgroundsbuttons[mData.get(position).getIdprinciple() - 1]);

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
                }).into(holder.ISToolImageView);
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


    public class ToolinterseachViewHolder extends RecyclerView.ViewHolder{
        ImageView ISToolImageView;
        TextView idtool, name, image, textvbuttonISresource, urltool, diccionario, description, idprinciple, principle,idguideline, guideline, idresource, resource, creationdate,updatedate,state;


        ToolinterseachViewHolder (View itemView){
            super(itemView);
            ISToolImageView = itemView.findViewById(R.id.ISToolImageView);
            name = itemView.findViewById(R.id.nameISresource);
          //  description = itemView.findViewById(R.id.descripISresource);
           // urltool = itemView.findViewById(R.id.linkISresource);
            textvbuttonISresource = itemView.findViewById(R.id.textvbuttonISresource);
        }
    }
}
