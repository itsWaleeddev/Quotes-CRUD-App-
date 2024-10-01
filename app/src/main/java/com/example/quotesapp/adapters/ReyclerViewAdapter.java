package com.example.quotesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quotesapp.ApiClient;
import com.example.quotesapp.DeleteResponse;
import com.example.quotesapp.MyInterface;
import com.example.quotesapp.R;
import com.example.quotesapp.UpdateActivity;
import com.example.quotesapp.databinding.SingleRowBinding;
import com.example.quotesapp.models.Data;


import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReyclerViewAdapter extends RecyclerView.Adapter<ReyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Data> dataList;
    MyInterface myInterface;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Retrofit retrofit;
    Drawable drawable;

    public ReyclerViewAdapter(Context context, List<Data> dataList, ActivityResultLauncher<Intent> activityResultLauncher, Drawable drawable) {
        this.context = context;
        this.dataList = dataList;
        this.drawable = drawable;
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
        holder.binding.getRoot().setBackground(drawable);
        holder.binding.authorName.setText(dataList.get(position).getAuthor_Name());
        holder.binding.quoteData.setText(dataList.get(position).getQuote());
        holder.binding.datetime.setText(dataList.get(position).getData_Time());
        holder.binding.deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteQuote(dataList.get(holder.getAdapterPosition()).getId() , holder.getAdapterPosition());
            }
        });
        holder.binding.updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("QuoteData", dataList.get(holder.getAdapterPosition()).getQuote());
                intent.putExtra("id", Integer.parseInt(dataList.get(holder.getAdapterPosition()).getId()));
                intent.putExtra("position", holder.getAdapterPosition());
                Log.d("id", "onClick: " + dataList.get(holder.getAdapterPosition()).getId());
                activityResultLauncher.launch(intent);
            }
        });
    }

    //How many Items?
    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SingleRowBinding binding;

        public ViewHolder(@NonNull SingleRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void deleteQuote(String id, int position) {
        myInterface.deletePoetry(id).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Quote Deleted Successfully", Toast.LENGTH_SHORT).show();
                    if (response.body().getStatus().equals("1")) {
                        dataList.remove(position);
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

