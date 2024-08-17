package com.example.visioneerapp;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);


    }

    LoginResponse loginResponse;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            loginResponse = (LoginResponse) bundle.getSerializable("data");
            Log.e("TAG", "======> "+"data now in HomeFragment for user " + loginResponse.getEmail());

            // Use the loginResponse here
            // For example, set the username to a TextView
            TextView nameTV = view.findViewById(R.id.textName);
            nameTV.setText(loginResponse.getUsername());
            TextView gmailTV = view.findViewById(R.id.textGmail);
            gmailTV.setText(loginResponse.getEmail());

            Button saveButton = view.findViewById(R.id.saveButton);
            Button cancelButton = view.findViewById(R.id.cancelButton);
            EditText email = view.findViewById(R.id.editTextEmail2);
            EditText password = view.findViewById(R.id.editTextPassword2);
            EditText username = view.findViewById(R.id.editTextUsername2);
            EditText confirmPassword = view.findViewById(R.id.editTextConfirmPassword2);

    saveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String emailStr = email.getText().toString();
            String usernameStr = username.getText().toString();
            String passwordStr = password.getText().toString();


            UpdateUserRequest updateUserRequest = new UpdateUserRequest();
            if (!usernameStr.isEmpty()) {
                updateUserRequest.setUsername(usernameStr);
            }
            if (!emailStr.isEmpty()) {
                if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                    email.setError("Enter a valid email");
                    email.requestFocus();
                } else {
                    updateUserRequest.setEmail(emailStr);
                }
            }

            if (!passwordStr.isEmpty()) {
                if (passwordStr.length() < 8) {
                    password.setError("Password should be at least 8 character");
                    password.requestFocus();
                } else if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
                    confirmPassword.setError("Confirm Password is wrong");
                    confirmPassword.requestFocus();
                } else {
                    updateUserRequest.setPassword(passwordStr);
                }
            }



            // Create a HashMap for the headers
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("token", loginResponse.getToken());

            Call<UpdateUserResponse> call = ApiClient.getService().updateUser(loginResponse.get_id(), updateUserRequest, headers);
            call.enqueue(new Callback<UpdateUserResponse>() {
                @Override
                public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                    if (response.isSuccessful()) {
                        UpdateUserResponse updateUserResponse = response.body();
                        if (updateUserResponse != null) {
                            // Update the UI with the updated user info
                            nameTV.setText(updateUserResponse.getMessage().getUsername());
                            gmailTV.setText(updateUserResponse.getMessage().getEmail());
                            // Add more UI updates here as needed
                            Toast.makeText(getActivity(), "User info updated successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Failed to update user info", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.e("UpdateUser", "Failed to update user info. Status code: " + response.code());
                        try {
                            Log.e("UpdateUser", "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UpdateUserResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // Create a new instance of HomeFragment
                    HomeFragment homeFragment = new HomeFragment();

                    // Create a new Bundle to hold the arguments
                    Bundle args = new Bundle();

                    // Put the LoginResponse object into the Bundle
                    args.putSerializable("data", loginResponse);

                    // Set the arguments for the HomeFragment
                    homeFragment.setArguments(args);

                    // Replace the current fragment with the new instance of HomeFragment
                    fragmentTransaction.replace(R.id.profile, homeFragment);
                    fragmentTransaction.commit();
                }
            });
}
}
}