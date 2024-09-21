package com.example.poetryapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPoetry extends AppCompatActivity {
    Toolbar toolbar;
    EditText poetry_data, poet_name;
    AppCompatButton addButton;
    MyInterface myInterface;
    String poetry = null;
    String poetName = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_poetry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initialization();
        setToolbar();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poetry = poetry_data.getText().toString();
                poetName = poet_name.getText().toString();
                if(poetry.isEmpty()){
                    poetry_data.setError("Enter a data to add");
                }
                else
                {
                    if (poetName.isEmpty()) {
                        poet_name.setError("Enter a poet name");
                    } else {
                        callApi();
                    }
                }
            }
        });
    }
    private void initialization(){
        toolbar = findViewById(R.id.addpoetrytoolbar);
        poetry_data = findViewById(R.id.poetry_data);
        poet_name = findViewById(R.id.poet_name);
        addButton = findViewById(R.id.addButton);
        Retrofit retrofit = ApiClient.getClient();
        myInterface = retrofit.create(MyInterface.class);
    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("poetry", poetry);
                intent.putExtra("poet_name", poetName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    private void callApi(){
        myInterface.addPoetry(poetry, poetName).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if(response.body().getStatus().equals("1")){
                    Toast.makeText(AddPoetry.this, "Data Successfully added", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(AddPoetry.this, "Failed to add data", Toast.LENGTH_SHORT).show();
                Log.d("failure", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}