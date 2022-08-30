package com.example.example;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient httpClient = new OkHttpClient();

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
        EditText usernameInput = (EditText) findViewById(R.id.editUsername);
        EditText passwordInput = (EditText) findViewById(R.id.editPassword);
        String username = String.valueOf(usernameInput.getText());
        String password = String.valueOf(passwordInput.getText());
        String json = "{'username':'" + username + "'," + "'password:'" + password + "}";
        RequestBody requestBody = RequestBody.create(json, JSON);
        Request request = new Request.Builder().url("http://{ip}:8080/api/users/login").post(requestBody).build();
        try (Response response = httpClient.newCall(request).execute()) {
            TextView textView = (TextView) findViewById(R.id.resultView);
            textView.setText("logged in");
            System.out.println(response.body().string());
        }
    }
}