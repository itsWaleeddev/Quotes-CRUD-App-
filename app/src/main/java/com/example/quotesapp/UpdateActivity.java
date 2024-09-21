package com.example.quotesapp;

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

import com.example.quotesapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText updateQuoteData;
    AppCompatButton submitButton;
    String quoteData  = null;
    int id;
    MyInterface myInterface;
    String updatedData = null;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initialization();
        setToolbar();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updateQuoteData.toString().isEmpty()){
                    updateQuoteData.setError("Field is Empty");
                }
                else{
                    callUpdateApi();
                }
            }
        });
    }
    private void initialization(){
        toolbar = findViewById(R.id.updatetoolbar);
        updateQuoteData = findViewById(R.id.updatequotedata);
        submitButton = findViewById(R.id.submitbutton);
        quoteData = getIntent().getStringExtra("QuoteData");
        id = getIntent().getIntExtra("id", 0);
        position = getIntent().getIntExtra("position", 0);
        updateQuoteData.setText(quoteData);
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
              intent.putExtra("position", position);
              intent.putExtra("updatedData", updatedData);
              setResult(RESULT_OK, intent);
              finish();
          }
      });
    }
    private void callUpdateApi(){
        updatedData = updateQuoteData.getText().toString();
        myInterface.updatePoetry(updatedData, id).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                try {
                    if(response.body().getStatus().equals("1")){
                        Toast.makeText(UpdateActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(UpdateActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                        Log.d("notUpdated", "onResponse: " + response.body().toString());
                    }
                }
                catch (Exception e){
                    Log.d("exception", "onResponse: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Log.d("failure", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}