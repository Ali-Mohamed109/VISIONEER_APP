package com.example.visioneerapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Design_two_UI extends AppCompatActivity {

    LoginResponse loginResponse2;
    GenerateImageResponse2 generateImageResponse2;
    ProgressBar progressBar2;
    Button dwgBT2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_design_two_ui);

        progressBar2 = findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.GONE);

        dwgBT2 = findViewById(R.id.DWG_Converter_And_Download_BT2);
        dwgBT2.setVisibility(View.GONE);

        Intent intent = getIntent();
        if (intent.getExtras()!=null){
            loginResponse2 =(LoginResponse)intent.getSerializableExtra("data");

            Log.e("TAG","======> "+"data now in designUI two for user "+loginResponse2.getEmail());

        }

        Spinner spinnerLivingRooms2 = findViewById(R.id.spinnerLivingRooms2);
        Spinner spinnerBedRooms2 = findViewById(R.id.spinnerBedRooms2);
        Spinner spinnerBathRooms2 = findViewById(R.id.spinnerBathRooms2);
        Spinner spinnerKitchens2 = findViewById(R.id.spinnerKitchens2);
        Button submitBT_forGenerateImage2= findViewById(R.id.SubmitBT_forGenerateImage2);
        ImageView imageViewForGenerateImage2 =findViewById(R.id.imageViewForGenerateImage2);

        submitBT_forGenerateImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String LivingRooms2 = spinnerLivingRooms2.getSelectedItem().toString();
                String BedRooms2 = spinnerBedRooms2.getSelectedItem().toString();
                String BathRooms2 = spinnerBathRooms2.getSelectedItem().toString();
                String Kitchens2 = spinnerKitchens2.getSelectedItem().toString();

                GenerateImageRequest2 generateImageRequest2 = new GenerateImageRequest2();

                generateImageRequest2.setLiving_rooms(LivingRooms2);
                generateImageRequest2.setBedrooms(BedRooms2);
                generateImageRequest2.setBathrooms(BathRooms2);
                generateImageRequest2.setKitchens(Kitchens2);


                // Create a HashMap for the headers
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("token", loginResponse2.getToken());

                // Show the ProgressBar
                progressBar2.setVisibility(View.VISIBLE);
                // Call the generateImage method
                Call<GenerateImageResponse2> call = ApiClient.getService().generateImage2(loginResponse2.get_id(), generateImageRequest2, headers);
                call.enqueue(new Callback<GenerateImageResponse2>() {
                    @Override
                    public void onResponse(Call<GenerateImageResponse2> call, Response<GenerateImageResponse2> response) {
                        if (response.isSuccessful()) {
                            generateImageResponse2 = response.body();
                            if (generateImageResponse2 != null) {
                                // Use Glide to load the image
                                Glide.with(Design_two_UI.this)
                                        .load(generateImageResponse2.getUrl())
                                        .into(imageViewForGenerateImage2);
                                // Make the dwgBT button visible
                                dwgBT2.setVisibility(View.VISIBLE);
                                // Set an OnClickListener on the ImageView
                                imageViewForGenerateImage2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Create an Intent to open the URL in a web browser
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(generateImageResponse2.getUrl()));
                                        startActivity(intent);
                                    }
                                });

                                Toast.makeText(Design_two_UI.this, "Image generated successfully", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Design_two_UI.this, "Failed to Image generate", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.e("Image generate", "Failed to Image generate. Status code: " + response.code());
                            try {
                                Log.e("Image generate", "Error body: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        // Hide the ProgressBar
                        progressBar2.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<GenerateImageResponse2> call, Throwable t) {
                        Toast.makeText(Design_two_UI.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        // Hide the ProgressBar
                        progressBar2.setVisibility(View.GONE);
                    }
                });
            }
        });





        dwgBT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (generateImageResponse2 != null && generateImageResponse2.getUrl() != null) {
                    DWG_Request dwgRequest = new DWG_Request();
                    dwgRequest.setUrl(generateImageResponse2.getUrl());

                    // Show the ProgressBar
                    progressBar2.setVisibility(View.VISIBLE);

                    Call<DWG_Response> call = ApiClient.getService().DWG_Converter(dwgRequest);
                    call.enqueue(new Callback<DWG_Response>() {
                        @Override
                        public void onResponse(Call<DWG_Response> call, Response<DWG_Response> response) {
                            if (response.isSuccessful()) {
                                DWG_Response dwgResponse = response.body();
                                if (dwgResponse != null) {
                                    // Get the DownloadManager service
                                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                                    // Create a DownloadManager.Request with the download URL
                                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(dwgResponse.getUrll()));
                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "converted_image.dwg");

                                    // Enqueue the download
                                    downloadManager.enqueue(request);

                                    Toast.makeText(Design_two_UI.this, "Downloading DWG file...", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(Design_two_UI.this, "Failed to convert DWG file", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.e("DWG file convert", "Failed to convert DWG file. Status code: " + response.code());
                                try {
                                    Log.e("DWG file convert", "Error body: " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            // Hide the ProgressBar
                            progressBar2.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<DWG_Response> call, Throwable t) {
                            Toast.makeText(Design_two_UI.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();

                            // Hide the ProgressBar
                            progressBar2.setVisibility(View.GONE);
                        }
                    });
                } else {
                    Toast.makeText(Design_two_UI.this, "No image to convert", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}