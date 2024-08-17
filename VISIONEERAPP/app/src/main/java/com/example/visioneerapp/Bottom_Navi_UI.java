package com.example.visioneerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Bottom_Navi_UI extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    LoginResponse loginResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navi_ui);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        frameLayout = findViewById(R.id.frameLayout);

        Intent intent = getIntent();
        if (intent.getExtras()!=null){
            loginResponse =(LoginResponse)intent.getSerializableExtra("data");

            Log.e("TAG","======> "+"data now in Bottom_Navi_UI for user "+loginResponse.getEmail());

        }



            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.Home) {
                    loadFragment(new HomeFragment(),false, loginResponse);

                } else if (itemId == R.id.Profile) {

                    loadFragment(new ProfileFragment(),false, loginResponse);

                } else if (itemId == R.id.Settings) {

                    loadFragment(new SettingsFragment(),false, loginResponse);
                }

                return true;
            }
        });

        loadFragment(new HomeFragment(),true, loginResponse);
    }

    private void loadFragment (Fragment fragment , boolean isAppInitialized, LoginResponse loginResponse){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();

        if(loginResponse != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", loginResponse);
            fragment.setArguments(bundle);
        }

        if(isAppInitialized){
            fragmentTransaction.add(R.id.frameLayout,fragment);
        }
        else {
            fragmentTransaction.replace(R.id.frameLayout,fragment);
        }

        fragmentTransaction.commit();
    }
}
//        loadFragment(new HomeFragment(),true);
//    }
//    private void loadFragment (Fragment fragment , boolean isAppInitialized){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
//
//        if(isAppInitialized){
//
//            fragmentTransaction.add(R.id.frameLayout,fragment);
//        }
//        else {
//            fragmentTransaction.replace(R.id.frameLayout,fragment);
//        }
//
//        fragmentTransaction.commit();
//
//    }
//}


