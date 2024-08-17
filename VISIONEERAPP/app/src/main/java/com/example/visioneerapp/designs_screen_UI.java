package com.example.visioneerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class designs_screen_UI extends AppCompatActivity {
    LoginResponse loginResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_designs_screen_ui);
        Intent intent = getIntent();
        if (intent.getExtras()!=null){
            loginResponse =(LoginResponse)intent.getSerializableExtra("data");

            Log.e("TAG","======> "+"data now in designsScreenUI for user "+loginResponse.getEmail());

        }

        ImageView Schematics_BT = findViewById(R.id.Schematics);
        ImageView Designs_layout_BT = findViewById(R.id.Designs_layout);

        Schematics_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Designs_one_UI.class);
                intent.putExtra("data", loginResponse);
                startActivity(intent);
            }
        });
        Designs_layout_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Design_two_UI.class);
                intent.putExtra("data", loginResponse);
                startActivity(intent);

            }

        });
    }
}