package com.example.poetryapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poetryapp.ApiClient;
import com.example.poetryapp.DeleteResponse;
import com.example.poetryapp.MyInterface;
import com.example.poetryapp.UpdateActivity;
import com.example.poetryapp.databinding.SingleRowBinding;
import com.example.poetryapp.models.PoetryInfo;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReyclerViewAdapter extends RecyclerView.Adapter<ReyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<PoetryInfo> poetryInfoList;
    MyInterface myInterface;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Retrofit retrofit;
    public ReyclerViewAdapter(Context context, List<PoetryInfo> poetryInfoList, ActivityResultLauncher<Intent> activityResultLauncher) {
        this.context = context;
        this.poetryInfoList = poetryInfoList;
        this.activityResultLauncher = activityResultLauncher;
        retrofit = ApiClient.getClient();
        myInterface = retrofit.create(MyInterface.class);
    }

    // where to get the single card as a viewholder object
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleRowBinding binding = SingleRowBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    //what will happen after we create the viewholder object
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.poetname.setText(poetryInfoList.get(position).getPoet_name());
        holder.binding.poetrydata.setText(poetryInfoList.get(position).getPoetry_data());
        holder.binding.datetime.setText(poetryInfoList.get(position).getData_Time());
        holder.binding.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePoetry(poetryInfoList.get(holder.getAdapterPosition()).getId() , holder.getAdapterPosition());
            }
        });
        holder.binding.updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("PoetryData", poetryInfoList.get(holder.getAdapterPosition()).getPoetry_data());
                intent.putExtra("id", Integer.parseInt(poetryInfoList.get(holder.getAdapterPosition()).getId()));
                intent.putExtra("position", holder.getAdapterPosition());
                Log.d("id", "onClick: " + poetryInfoList.get(holder.getAdapterPosition()).getId());
                activityResultLauncher.launch(intent);
            }
        });
    }

    //How many Items?
    @Override
    public int getItemCount() {
        return poetryInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SingleRowBinding binding;

        public ViewHolder(@NonNull SingleRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void deletePoetry(String id, int position) {
        myInterface.deletePoetry(id).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show();
                    if (response.body().getStatus().equals("1")) {
                        poetryInfoList.remove(position);
                        notifyDataSetChanged();
                    }
                }

            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.d("deleteFailure", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}

