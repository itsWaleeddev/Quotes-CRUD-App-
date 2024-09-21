package com.example.poetryapp;

import static android.app.ProgressDialog.show;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.poetryapp.adapters.ReyclerViewAdapter;
import com.example.poetryapp.models.PoetryInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ReyclerViewAdapter adapter;
    MyInterface myInterface;
    Toolbar toolbar;
    List<PoetryInfo> poetryInfoList = new ArrayList<>();
    private ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode()==RESULT_OK && result.getData()!=null){
                int position = result.getData().getIntExtra("position", 0);
                String updatedData = result.getData().getStringExtra("updatedData");
                String poetry = result.getData().getStringExtra("poetry");
                String poetName = result.getData().getStringExtra("poet_name");
                if(updatedData!=null){
                    poetryInfoList.get(position).setPoetry_data(updatedData);
                    adapter.notifyItemChanged(position);
                }
                if(poetry!=null && poetName!=null){
                    getData();
                }
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
                            poetryInfoList = apiResponse.getData();
                            Log.d("checkapi", "onResponse: " + apiResponse.toString());
                            setAdapter(poetryInfoList);
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

    private void setAdapter(List<PoetryInfo> poetryModels) {
        adapter = new ReyclerViewAdapter(this, poetryModels, activityResultLauncher);
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
       if(id == R.id.add_poetry){
           Intent intent = new Intent(MainActivity.this, AddPoetry.class);
           activityResultLauncher.launch(intent);
           return true;
       }
       else{
           return super.onOptionsItemSelected(item);
       }
    }
}