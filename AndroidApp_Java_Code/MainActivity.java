package com.example.myiothome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    TextView lastUpdateTxt, tempTxt, humiTxt, statusTxt;
    ProgressBar tempBar, humiBar;
    EditText get_device_Ip;

    private Handler handler = new Handler(Looper.getMainLooper());
    private OkHttpClient client = new OkHttpClient();
    private String baseUrl;

    @SuppressLint("QueryPermissionsNeeded")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastUpdateTxt = findViewById(R.id.update);
        tempTxt = findViewById(R.id.temp_value);
        humiTxt = findViewById(R.id.humi_value);
        statusTxt = findViewById(R.id.status);
        tempBar = findViewById(R.id.progressBar_Temp);
        humiBar = findViewById(R.id.progressBar_Humi);
        get_device_Ip = findViewById(R.id.id_to_connect);

        FloatingActionButton fab = findViewById(R.id.app_info);
        fab.setOnClickListener(v -> {
            String githubReadmeUrl = "https://github.com/Sukarnascience/MyIOTHome/";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubReadmeUrl));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "No web browser available.", Toast.LENGTH_SHORT).show();
                //Snackbar.make(MainActivity.this, "No web browser available.", Snackbar.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton connectButton = findViewById(R.id.connect_button);
        connectButton.setOnClickListener(v -> {
            fetchData();
        });
    }

    private void fetchData() {
        String ipAddress = get_device_Ip.getText().toString();
        baseUrl = "http://" + ipAddress; // Include the full URL here

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchDataFromUrl();
                handler.postDelayed(this, 1000); // Fetch data every second
            }
        }, 1000);
    }

    @SuppressLint("SetTextI18n")
    private void fetchDataFromUrl() {
        Request request = new Request.Builder()
                .url(baseUrl)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JSONObject jsonObject = new JSONObject(responseBody);

                int status = jsonObject.getInt("status");
                if (status == 200) {
                    String deviceId = jsonObject.getString("device_id");
                    int temperature = jsonObject.getInt("temperature");
                    int humidity = jsonObject.getInt("humidity");

                    lastUpdateTxt.setText("Device ID: " + deviceId);
                    statusTxt.setText("Status: 200");
                    tempTxt.setText(temperature + "°C");
                    humiTxt.setText(humidity + "%");
                    tempBar.setProgress(temperature);
                    humiBar.setSecondaryProgress(humidity);
                } else {
                    String deviceId = jsonObject.getString("device_id");
                    lastUpdateTxt.setText("Device ID: " + deviceId);
                    statusTxt.setText("Status: " + status);
                }
            } else {
                statusTxt.setText("Status: Unreachable");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            statusTxt.setText("Status: IO/JSON Error");
        }
    }

}

