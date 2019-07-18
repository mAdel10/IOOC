package com.ioocllcdrdapp.iooc.controllers.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;

public class MoocHolder extends RecyclerView.ViewHolder{


    public RecyclerView moocRecyclerView;
    public TextView moocPlatformTV;

    public MoocHolder(View view) {
        super(view);

        moocRecyclerView = (RecyclerView) view.findViewById(R.id.mooc_recycle_view);
        moocPlatformTV = view.findViewById(R.id.moocTitle);

    }
}
