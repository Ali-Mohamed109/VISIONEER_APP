package com.example.visioneerapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class SettingsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
    LoginResponse loginResponse;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            LoginResponse loginResponse = (LoginResponse) bundle.getSerializable("data");
            Log.e("TAG", "======> "+"data now in HomeFragment for user " + loginResponse.getEmail());

            // Use the loginResponse here
            // For example, set the username to a TextView
//           loginResponse.getEmail()
        }
    }
}
