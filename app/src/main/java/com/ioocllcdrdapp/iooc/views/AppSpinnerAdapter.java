package com.ioocllcdrdapp.iooc.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.helpers.Constants;

import java.util.List;

public class AppSpinnerAdapter<T> extends ArrayAdapter<T> {

    private Typeface font = Typeface.createFromAsset(getContext().getAssets(), Constants.FONT_GOTHAM_BOOK);

    public AppSpinnerAdapter(Context context, int resource, List<T> items) {
        super(context, resource, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTypeface(font);
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTypeface(font);
        return view;
    }
}