package com.example.quotesapp;

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

import com.example.quotesapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddQuote extends AppCompatActivity {
    Toolbar toolbar;
    EditText quote, Author_Name;
    AppCompatButton addButton;
    MyInterface myInterface;
    String Quote = null;
    String authorName = null;
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
                Quote = quote.getText().toString();
                authorName = Author_Name.getText().toString();
                if(Quote.isEmpty()){
                    quote.setError("Write a quote to add");
                }
                else
                {
                    if (authorName.isEmpty()) {
                        Author_Name.setError("Enter a Author name");
                    } else {
                        callApi();
                    }
                }
            }
        });
    }
    private void initialization(){
        toolbar = findViewById(R.id.Addtoolbar);
        quote = findViewById(R.id.quote);
        Author_Name = findViewById(R.id.Author_Name);
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
                setResult(RESULT_OK);
                finish();
            }
        });
    }
    private void callApi(){
        myInterface.addPoetry(Quote, authorName).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if(response.body().getStatus().equals("1")){
                    Toast.makeText(AddQuote.this, "Data Successfully added", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(AddQuote.this, "Failed to add data", Toast.LENGTH_SHORT).show();
                Log.d("failure", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}