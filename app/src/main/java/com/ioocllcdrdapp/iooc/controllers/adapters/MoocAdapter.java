package com.ioocllcdrdapp.iooc.controllers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ioocllcdrdapp.iooc.R;
import com.ioocllcdrdapp.iooc.backend.api.ApiClient;
import com.ioocllcdrdapp.iooc.backend.api.ApiInterface;
import com.ioocllcdrdapp.iooc.backend.models.MoocCourse;
import com.ioocllcdrdapp.iooc.backend.models.MoocPlatform;
import com.ioocllcdrdapp.iooc.controllers.holders.MoocHolder;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoocAdapter extends RecyclerView.Adapter<MoocHolder> {

    private static final String TAG = "OperationsManager";
    private List<MoocPlatform> moocPlatforms;
    private Context context;
    public MoocAdapter(List<MoocPlatform> moocPlatforms, Context context) {
        this.moocPlatforms = moocPlatforms;
        this.context = context;
    }

    private Context getContext() {
        return context;
    }

    @NonNull
    @Override
    public MoocHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mooc_platform, parent, false);
        return new MoocHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MoocHolder holder, int position) {
        final MoocPlatform moocPlatform = moocPlatforms.get(position);
        holder.moocPlatformTV.setText(moocPlatform.getName());
        holder.moocRecyclerView.setHasFixedSize(true);
        holder.moocRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false));
        getMoocCourses(holder.moocRecyclerView, moocPlatform.getId());
    }

    @Override
    public int getItemCount() {
        return moocPlatforms.size();

    }

    public void getMoocCourses(RecyclerView recyclerView, String id) {
        Log.v(TAG, "doGetMoocCourses");
        HashMap<String, String> headers = ApiClient.getDefaultHeaders();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<MoocCourse>> call = apiService.doGetMoocCourses(headers, id);

        call.enqueue(new Callback<List<MoocCourse>>() {
            @Override
            public void onResponse(@NonNull Call<List<MoocCourse>> call, @NonNull Response<List<MoocCourse>> response) {
                if (response.code() == 200) {
                    List<MoocCourse> moocCourses = response.body();
                    MoocCourseAdapter moocCourseAdapter = new MoocCourseAdapter(moocCourses, context);
                    recyclerView.setAdapter(moocCourseAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MoocCourse>> call, @NonNull Throwable t) {

            }
        });
    }
}
