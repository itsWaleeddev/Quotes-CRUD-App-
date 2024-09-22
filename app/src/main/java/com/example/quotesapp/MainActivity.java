package com.example.quotesapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quotesapp.R;
import com.example.quotesapp.adapters.ReyclerViewAdapter;
import com.example.quotesapp.models.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ReyclerViewAdapter adapter;
    MyInterface myInterface;
    Toolbar toolbar;
    List<Data> dataList = new ArrayList<>();
    private final int[] drawableIds = {R.drawable.one, R.drawable.two};

    private ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()==RESULT_OK && result.getData()!=null){
                int position = result.getData().getIntExtra("position", 0);
                String updatedData = result.getData().getStringExtra("updatedData");
                if(updatedData!=null){
                    dataList.get(position).setQuote(updatedData);
                    adapter.notifyItemChanged(position);
                }
                getData();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Initialization();
        setSupportActionBar(toolbar);
        getData();

    }
    private void Initialization() {
        recyclerView = findViewById(R.id.myrecyclerView);
        myInterface = ApiClient.getClient().create(MyInterface.class);
        toolbar = findViewById(R.id.mytoolbar);
    }

    private void getData() {
        Call<ApiResponse> result = myInterface.getData();
        result.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("1")) {
                            ApiResponse apiResponse = response.body();
                            dataList = apiResponse.getData();
                            setAdapter(dataList);
                        } else {
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.d("exception", "onFailure: " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("failure", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    private void setAdapter(List<Data> poetryModels) {
        Random random = new Random();
        int randomIndex = random.nextInt(drawableIds.length);
        Drawable drawable = getResources().getDrawable(drawableIds[randomIndex]);
        adapter = new ReyclerViewAdapter(this, poetryModels, activityResultLauncher, drawable);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
       int id = item.getItemId();
       if(id == R.id.add_Quote){
           Intent intent = new Intent(MainActivity.this, AddQuote.class);
           activityResultLauncher.launch(intent);
           return true;
       }
       else{
           return super.onOptionsItemSelected(item);
       }
    }
}