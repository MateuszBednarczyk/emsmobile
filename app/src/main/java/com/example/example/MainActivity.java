package com.example.example;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button button = findViewById(R.id.sendRequestBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendTestRequest(view);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendTestRequest(View view) throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.254.153:8080/api/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);
        EditText usernameInput = (EditText) findViewById(R.id.editUsername);
        EditText passwordInput = (EditText) findViewById(R.id.editPassword);
        String username = String.valueOf(usernameInput.getText());
        String password = String.valueOf(passwordInput.getText());
        TextView textView = (TextView) findViewById(R.id.resultView);
        retrofitService.login(new UserCredentials(username, password), "application/json").enqueue(new Callback<UserCredentials>() {
            @Override
            public void onResponse(Call<UserCredentials> call, Response<UserCredentials> response) {
                if (response.code() == 200) {
                    Toast.makeText(MainActivity.this, "logged in", Toast.LENGTH_SHORT).show();
                    textView.setText("logged in");
                } else {
                    Toast.makeText(MainActivity.this, "can't log in", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserCredentials> call, Throwable t) {
                Toast.makeText(MainActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                textView.setText("login failed");
            }
        });
    }
}