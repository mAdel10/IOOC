package com.ioocllcdrdapp.iooc.controllers.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;

public class MaterialHolder extends RecyclerView.ViewHolder{

    public TextView materialLink;
    public LinearLayout itemMaterial;

    public MaterialHolder(View view) {
        super(view);
        materialLink = view.findViewById(R.id.item_materials_link);
        itemMaterial = view.findViewById(R.id.item_materials_layout);
    }
}
