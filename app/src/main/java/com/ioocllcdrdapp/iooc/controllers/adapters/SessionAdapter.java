package com.ioocllcdrdapp.iooc.controllers.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.activities.SessionDetailsActivity;
import com.ioocllcdrdapp.iooc.backend.models.Group;
import com.ioocllcdrdapp.iooc.backend.models.Session;
import com.ioocllcdrdapp.iooc.controllers.holders.SessionHolder;
import com.ioocllcdrdapp.iooc.helpers.Constants;
import com.ioocllcdrdapp.iooc.managers.UserManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionHolder>  {
    private List<Session> sessions;
    private Group group;
    private Context context;

    public SessionAdapter(List<Session> sessions, Group group, Context context) {
        this.sessions = sessions;
        this.group = group;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public SessionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_session, parent, false);
        return new SessionHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionHolder holder, int position) {
        Session session = sessions.get(position);
        session.setSessionNumber("Session " + (position + 1));
        holder.titleTextView.setText(session.getSessionNumber());
        holder.itemView.setOnClickListener(view -> {

            if (UserManager.getInstance().isStudent()) {
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                try {
                    Date mDate;
                    try {
                        mDate = sdf.parse(session.getDate());
                        long timeInMilliseconds = mDate.getTime();
                        Calendar rightNow = Calendar.getInstance();

                        if (  rightNow.getTimeInMillis()> timeInMilliseconds) {
                            Intent i = new Intent(context, SessionDetailsActivity.class);
                            i.putExtra(Constants.INTENT_SESSION, session);
                            i.putExtra(Constants.INTENT_GROUP, group);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);

                        } else {
                            Toast.makeText(context, "Upcoming Session..", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (!UserManager.getInstance().isStudent()) {
                Intent i = new Intent(context, SessionDetailsActivity.class);
                i.putExtra(Constants.INTENT_SESSION, session);
                i.putExtra(Constants.INTENT_GROUP, group);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
    }
    @Override
    public int getItemCount() {
        return sessions.size();
    }
}
