package com.example.visioneerapp;

import android.app.Activity;
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

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Designs_one_UI extends Activity {
    LoginResponse loginResponse;
    GenerateImageResponse generateImageResponse;
    ProgressBar progressBar;
    Button dwgBT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designs_one_ui);
        Button dwgBT = findViewById(R.id.DWG_Converter_And_Download_BT);
        dwgBT.setVisibility(View.GONE);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        Intent intent = getIntent();
        if (intent.getExtras()!=null){
            loginResponse =(LoginResponse)intent.getSerializableExtra("data");

            Log.e("TAG","======> "+"data now in designUI one for user "+loginResponse.getEmail());

        }

        Spinner spinnerLivingRooms = findViewById(R.id.spinnerLiving_Rooms);
        Spinner spinnerBedRooms = findViewById(R.id.spinnerBedRooms);
        Spinner spinnerBathRooms = findViewById(R.id.spinnerBathRooms);
        Spinner spinnerKitchens = findViewById(R.id.spinnerKitchens);
        Spinner spinnerStairs = findViewById(R.id.spinnerStairs);
        Spinner spinnerGarage = findViewById(R.id.spinnerGarage);
        Spinner spinnerGarden = findViewById(R.id.spinnerGarden);
        Spinner spinnerBalcony = findViewById(R.id.spinnerBalcony);
        Spinner spinnerHall = findViewById(R.id.spinnerHall);
        Spinner spinnerStore= findViewById(R.id.spinnerStore);
        Spinner spinnerEntrence= findViewById(R.id.spinnerEntrence);
        Spinner spinnerWareHouse = findViewById(R.id.spinnerWareHouse);
        Spinner spinnerEmptySpace = findViewById(R.id.spinnerEmptySpace);
        Button submitBT_forGenerateImage= findViewById(R.id.SubmitBT_forGenerateImage);
        ImageView imageViewForGenerateImage =findViewById(R.id.imageViewForGenerateImage);

        submitBT_forGenerateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String LivingRooms = spinnerLivingRooms.getSelectedItem().toString();
                String BedRooms = spinnerBedRooms.getSelectedItem().toString();
                String BathRooms = spinnerBathRooms.getSelectedItem().toString();
                String Kitchens = spinnerKitchens.getSelectedItem().toString();
                String Stairs = spinnerStairs.getSelectedItem().toString();
                String Garage = spinnerGarage.getSelectedItem().toString();
                String Garden = spinnerGarden.getSelectedItem().toString();
                String Balcony = spinnerBalcony.getSelectedItem().toString();
                String Hall = spinnerHall.getSelectedItem().toString();
                String Store = spinnerStore.getSelectedItem().toString();
                String Entrence = spinnerEntrence.getSelectedItem().toString();
                String WareHouse = spinnerWareHouse.getSelectedItem().toString();
                String EmptySpace = spinnerEmptySpace.getSelectedItem().toString();

                GenerateImageRequest generateImageRequest = new GenerateImageRequest();

                generateImageRequest.setLiving_rooms(LivingRooms);
                generateImageRequest.setBedrooms(BedRooms);
                generateImageRequest.setBathrooms(BathRooms);
                generateImageRequest.setKitchens(Kitchens);
                generateImageRequest.setStairs(Stairs);
                generateImageRequest.setGarage(Garage);
                generateImageRequest.setGarden(Garden);
                generateImageRequest.setBalcony(Balcony);
                generateImageRequest.setHall(Hall);
                generateImageRequest.setStore(Store);
                generateImageRequest.setEntrance(Entrence);
                generateImageRequest.setWarehouse(WareHouse);
                generateImageRequest.setEmpty_space(EmptySpace);

                // Create a HashMap for the headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("token", loginResponse.getToken());

                // Show the ProgressBar
                progressBar.setVisibility(View.VISIBLE);
                // Call the generateImage method
        Call<GenerateImageResponse> call = ApiClient.getService().generateImage(loginResponse.get_id(), generateImageRequest, headers);
        call.enqueue(new Callback<GenerateImageResponse>() {
            @Override
            public void onResponse(Call<GenerateImageResponse> call, Response<GenerateImageResponse> response) {
                if (response.isSuccessful()) {
                     generateImageResponse = response.body();
                    if (generateImageResponse != null) {
                        // Use Glide to load the image
                        Glide.with(Designs_one_UI.this)
                                .load(generateImageResponse.getUrl())
                                .into(imageViewForGenerateImage);
                        // Make the dwgBT button visible
                        dwgBT.setVisibility(View.VISIBLE);
                        // Set an OnClickListener on the ImageView
                        imageViewForGenerateImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Create an Intent to open the URL in a web browser
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(generateImageResponse.getUrl()));
                                startActivity(intent);
                            }
                        });

                        Toast.makeText(Designs_one_UI.this, "Image generated successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Designs_one_UI.this, "Failed to Image generate", Toast.LENGTH_LONG).show();
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
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<GenerateImageResponse> call, Throwable t) {
                Toast.makeText(Designs_one_UI.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                // Hide the ProgressBar
                progressBar.setVisibility(View.GONE);
            }
        });
    }
});





        dwgBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (generateImageResponse != null && generateImageResponse.getUrl() != null) {
                    DWG_Request dwgRequest = new DWG_Request();
                    dwgRequest.setUrl(generateImageResponse.getUrl());

                    // Show the ProgressBar
                    progressBar.setVisibility(View.VISIBLE);

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

                                    Toast.makeText(Designs_one_UI.this, "Downloading DWG file...", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(Designs_one_UI.this, "Failed to convert DWG file", Toast.LENGTH_LONG).show();
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
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<DWG_Response> call, Throwable t) {
                            Toast.makeText(Designs_one_UI.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();

                            // Hide the ProgressBar
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                } else {
                    Toast.makeText(Designs_one_UI.this, "No image to convert", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}