package com.ioocllcdrdapp.iooc.controllers.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.activities.ChatActivity;
import com.ioocllcdrdapp.iooc.activities.SessionActivity;
import com.ioocllcdrdapp.iooc.backend.models.Group;
import com.ioocllcdrdapp.iooc.controllers.holders.GroupHolder;
import com.ioocllcdrdapp.iooc.helpers.Constants;

import java.util.List;

import static com.ioocllcdrdapp.iooc.utilities.MyApplication.getContext;

public class GroupsAdapter extends RecyclerView.Adapter<GroupHolder> {

    private List<Group> groups;
    private Context context;
    private int mode;
    public static int LAND_SCAPE = R.layout.item_group_landscape;
    private Activity activity;

    public GroupsAdapter(List<Group> groups, Context context, int mode, Activity activity ) {
        this.groups = groups;
        this.context = context;
        this.mode = mode;
        this.activity = activity;
    }

    public GroupsAdapter(List<Group> groups, Context context, int mode) {
        this.groups = groups;
        this.context = context;
        this.mode = mode;
    }

    private Context getContext() {
        return context;
    }


    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(mode, parent, false);
        return new GroupHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupHolder holder, int position) {
        final Group group = groups.get(position);
        if (group.getCourse() != null)
            holder.groupName.setText(String.valueOf(group.getCourse().getName()));
        holder.groupStartDate.setText(String.valueOf(group.getStartDate()));
        holder.itemView.setOnClickListener(v -> {
            openGroupDialog(group);
        });


    }

    public void openGroupDialog(Group group) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams")
        View dialogView = inflater.inflate(R.layout.dialog_choose_instructor, null);
        ImageView closeImageView = dialogView.findViewById(R.id.dialog_close_imageViews);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView details = dialogView.findViewById(R.id.dialog_details_textView);
        TextView chat = dialogView.findViewById(R.id.dialog_chat_textView);

        closeImageView.setOnClickListener(v -> dialog.dismiss());

        details.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(activity, SessionActivity.class);
                i.putExtra(Constants.INTENT_OBJECT, group);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(i);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(activity, ChatActivity.class);
                i.putExtra(Constants.INTENT_ID,group.getId());
                i.putExtra(Constants.INTENT_KEY,group.getCourse().getName());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}
