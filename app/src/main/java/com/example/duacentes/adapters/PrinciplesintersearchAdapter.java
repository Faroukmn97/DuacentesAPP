package com.example.duacentes.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
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
import com.example.duacentes.models.PrincipleModel;

import java.util.List;

public class PrinciplesintersearchAdapter extends RecyclerView.Adapter<PrinciplesintersearchAdapter.PrinciplesintersearchViewHolder> implements  View.OnClickListener{
    private List<PrincipleModel> mData;
    private LayoutInflater mInflater;
    private Context context;
    private View.OnClickListener listener;
    private String[] tipeprinciple = {
            "<b>Red de reconocimiento</b> del cerebro mediante la identificación de hechos, ideas y conceptos clave",

            "Canales auditivos, visuales o de forma impresa",

            "Conocimiento insuficiente de la lengua, problemas motrices, limitaciones en la memoria"};

    private int[] backgroundsbuttons = {
            R.drawable.buttonlearningrepresentation,
            R.drawable.buttonlearningacex,
            R.drawable.buttonlearningengagement,
    };

    public PrinciplesintersearchAdapter(List<PrincipleModel> ListPrinciple, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.mData = ListPrinciple;
        this.context = context;
    }

    @Override
    public PrinciplesintersearchAdapter.PrinciplesintersearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.element_intesearch_principle, parent,false);
        view.setOnClickListener(this);
        return new PrinciplesintersearchAdapter.PrinciplesintersearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrinciplesintersearchAdapter.PrinciplesintersearchViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getName());
        holder.tipeprinciple.setText(Html.fromHtml(tipeprinciple[position]));
       // holder.tipeprinciple.setText("Principio " + mData.get(position).getIdprinciple());
     //   Glide.with(context).load(mData.get(position).getImage()).into(holder.ImagePrinciple);
        holder.textvbuttonISprinciple.setBackgroundResource(backgroundsbuttons[mData.get(position).getIdprinciple() - 1]);

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

    public class PrinciplesintersearchViewHolder extends RecyclerView.ViewHolder{
        ImageView ImagePrinciple;
        TextView tipeprinciple;
        TextView textvbuttonISprinciple;
        TextView name;


        PrinciplesintersearchViewHolder (View itemView){
            super(itemView);
            ImagePrinciple = itemView.findViewById(R.id.ImagePrincipleintersearch);
            tipeprinciple = itemView.findViewById(R.id.tipeprincipleintersearch);
            name = itemView.findViewById(R.id.nameintersearchprinciple);
            textvbuttonISprinciple = itemView.findViewById(R.id.textvbuttonISprinciple);
        }
    }
}