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

public class SearchToolAdapter extends RecyclerView.Adapter<SearchToolAdapter.SearchToolViewHolder> implements View.OnClickListener {
    private List<ToolModel> mData;
    private ArrayList<ToolModel> mDataO;
    private ArrayList<ToolModel> mDatablanc;
    private LayoutInflater mInflater;
    private Context context;
    private View.OnClickListener listener;

    public SearchToolAdapter(List<ToolModel> ListSearchTool, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = ListSearchTool;
        this.context = context;
        this.mDataO = new ArrayList<>();
        mDataO.addAll(mData);
    }

    @Override
    public SearchToolAdapter.SearchToolViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.element_list_resource, parent, false);
        view.setOnClickListener(this);
        return new SearchToolAdapter.SearchToolViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchToolAdapter.SearchToolViewHolder holder, int position) {
        holder.name.setText(mData.get(position).getName());
     //   Glide.with(context).load(mData.get(position).getImage()).into(holder.SearchToolImageView);
        holder.name.setText(mData.get(position).getName());
     //   holder.description.setText(mData.get(position).getDescription());
      //  holder.urltool.setText(mData.get(position).getUrltool());

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
                }).into(holder.SearchToolImageView);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);
        }
    }

    public void Filtering(String searchtool) {

        int len = searchtool.length();

        if (len == 0) {
            mData.clear();
            mData.addAll(mDataO);
        } else {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    List<ToolModel> collection = mData.stream()
                            .distinct()
                            .filter(i -> i.getName().toLowerCase().contains(searchtool.toLowerCase()))
                            .collect(Collectors.toList());
                    mData.clear();
                    mData.addAll(collection);
                } else {
                    for (ToolModel c : mDataO) {
                        if (c.getName().toLowerCase().contains(searchtool.toLowerCase())) {
                            mData.add(c);
                        }
                    }
                }
        }
        notifyDataSetChanged();
    }

    public class SearchToolViewHolder extends RecyclerView.ViewHolder {
        ImageView SearchToolImageView;
        TextView name;


        SearchToolViewHolder(View itemView) {
            super(itemView);
            SearchToolImageView = itemView.findViewById(R.id.SearchToolImageView);
            name = itemView.findViewById(R.id.nameresourceshearch);
        }
    }
}
