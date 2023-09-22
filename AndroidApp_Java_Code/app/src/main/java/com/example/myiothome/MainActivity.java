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

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    TextView lastUpdateTxt, tempTxt, humiTxt, statusTxt;
    ProgressBar tempBar, humiBar;
    EditText get_device_Ip;

    private Handler handler = new Handler(Looper.getMainLooper());
    AsyncHttpClient client = new AsyncHttpClient();
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
        client.get(baseUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {

                try{
                    int status = json.jsonObject.getInt("status");
                    if(status == 200) {
                        String deviceId = json.jsonObject.getString("device_id");
                        int temperature = json.jsonObject.getInt("temperature");
                        int humidity = json.jsonObject.getInt("humidity");

                        lastUpdateTxt.setText("Device ID: " + deviceId);
                        statusTxt.setText("Status: 200");
                        tempTxt.setText(temperature + "°C");
                        humiTxt.setText(humidity + "%");
                        tempBar.setProgress(temperature);
                        humiBar.setSecondaryProgress(humidity);
                    } else {
                        String deviceId = json.jsonObject.getString("device_id");
                        lastUpdateTxt.setText("Device ID: " + deviceId);
                        statusTxt.setText("Status: " + status);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    statusTxt.setText("Status: JSON Error");
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                statusTxt.setText("Status: Unreachable");
            }
        });
    }

}

