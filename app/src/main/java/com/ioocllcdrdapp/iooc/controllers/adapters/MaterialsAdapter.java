package com.ioocllcdrdapp.iooc.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.controllers.holders.MaterialsHolder;

import java.util.List;

public class MaterialsAdapter extends RecyclerView.Adapter<MaterialsHolder> {

    private List<String> materials;
    private Context context;

    public MaterialsAdapter(List<String> materials, Context context) {
        this.materials = materials;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public MaterialsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_material, parent, false);
        return new MaterialsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialsHolder holder, int position) {
        holder.materialTextView.setText(materials.get(position));

        holder.closeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materials.remove(position);
                notifyDataSetChanged();

            }
        });
    }

    public void addItem(String newMaterial){
        materials.add(newMaterial);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return materials.size();
    }

    public List<String> getMaterial(){
        return materials;
    }
}
