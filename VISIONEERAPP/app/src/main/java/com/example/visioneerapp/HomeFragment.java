package com.example.visioneerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.ar.core.examples.java.helloar.HelloArActivity;


public class HomeFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    LoginResponse loginResponse;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            loginResponse = (LoginResponse) bundle.getSerializable("data");
            if (loginResponse != null) {
                Log.e("TAG", "======> " + "data now in HomeFragment for user " + loginResponse.getEmail());

                // Use the loginResponse here
                // For example, set the username to a TextView
//            TextView welcomeTextView = view.findViewById(R.id.WelcometextView);
//            welcomeTextView.setText("Welcome " + loginResponse.getUsername());

                String token = loginResponse.getToken();
                if (token != null) {
                    // Use the token here
                } else {
                    // Handle the case where the token is null
                }
            }
            ImageView designIcon = view.findViewById(R.id.DesignIcon);
            designIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), designs_screen_UI.class);
                    intent.putExtra("data", loginResponse);
                    startActivity(intent);
                    getActivity();
                }
            });

            ImageView arIcon = view.findViewById(R.id.ARIcon);
            arIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), HelloArActivity.class);
                    intent.putExtra("data", loginResponse);
                    startActivity(intent);
                    getActivity();
                }
            });

            ImageView onshape = view.findViewById(R.id.onshep);
            onshape.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),OnShape.class);
                    intent.putExtra("data", loginResponse);
                    startActivity(intent);
                    getActivity();
                }
            });
        }
    }

}