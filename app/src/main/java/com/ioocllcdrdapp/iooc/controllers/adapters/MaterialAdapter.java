package com.ioocllcdrdapp.iooc.controllers.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.models.Session;
import com.ioocllcdrdapp.iooc.controllers.holders.MaterialHolder;
import com.ioocllcdrdapp.iooc.controllers.holders.SessionHolder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialHolder> {

    private List<String> material;
    private Context context;

    public MaterialAdapter(List<String> material, Context context) {
        this.material = material;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public MaterialHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_materials, parent, false);
        return new MaterialHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialHolder holder, int position) {


        String link = material.get(position);
        holder.materialLink.setText(link);

        holder.itemView.setOnClickListener(v -> {
            Uri materialLink = Uri.parse(link);
            Intent i = new Intent(Intent.ACTION_VIEW,materialLink);
            getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        if (material == null) return 0; return material.size();
    }
}
