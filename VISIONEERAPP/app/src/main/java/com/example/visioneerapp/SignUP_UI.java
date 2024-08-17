package com.example.visioneerapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUP_UI extends AppCompatActivity {

    private EditText editTextUsername, editTextEmail,editTextPassword,editTextConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_ui);
        editTextUsername =findViewById(R.id.editTextUsername20);
        editTextEmail =findViewById(R.id.editTextemail);
        editTextPassword =findViewById(R.id.editTextpassword);
        editTextConfirmPassword=findViewById(R.id.editTextConfirmPassword);
        Button signUpBT = findViewById(R.id.SignUpButton);

        signUpBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String ConfirmPassword = editTextConfirmPassword.getText().toString();

                if (username.isEmpty()){
                    editTextUsername.setError("User Name is required");
                    editTextUsername.requestFocus();
                    return;
                }
                if (email.isEmpty()){
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    editTextEmail.setError("Enter a valid email");
                    editTextEmail.requestFocus();
                    return;
                }
                if (password.isEmpty()){
                    editTextPassword.setError("Password is required");
                    editTextPassword.requestFocus();
                    return;
                }
                if (password.length()<8){
                    editTextPassword.setError("Password should be at least 8 character");
                    editTextPassword.requestFocus();
                    return;
                }
                if (!ConfirmPassword.equals(password)){
                    editTextConfirmPassword.setError("Confirm Password is wrong");
                    editTextConfirmPassword.requestFocus();
                    return;
                }


                RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setEmail(email);
                registerRequest.setPassword(password);
                registerRequest.setUsername(username);
                registerUser(registerRequest);
            }
        });



    }

    public void registerUser(RegisterRequest registerRequest){

        Call<RegisterResponse> registerResponseCall =ApiClient.getService().registerUser(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.isSuccessful()){

                    String message ="Successful";
                    Toast.makeText(SignUP_UI.this,message,Toast.LENGTH_LONG).show();

                    startActivity(new Intent(SignUP_UI.this, Login_UI.class));
                    finish();
                }
                else {
                    String message ="User already registered";
                    Toast.makeText(SignUP_UI.this,message,Toast.LENGTH_LONG).show();


                }

            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

                String message =t.getLocalizedMessage();
                Toast.makeText(SignUP_UI.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}




//        Call<ResponseBody>call =RetrofitClient.
//                getInstance()
//                .getApi()
//                .CreateUser(email,username,password);
//
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                try {
//                    String s= response.body().string();
////                   Toast.makeText(SignUP_UI.this,s,Toast.LENGTH_LONG).show();
//                    System.out.println(s);
//
//                    Toast.makeText(SignUP_UI.this,"Successful",Toast.LENGTH_LONG).show();
////                    go to login Ui
//                    Intent intent = new Intent(getBaseContext(), Login_UI.class);
//                    startActivity(intent);
//
//                } catch (IOException e) {
//
//                    throw new RuntimeException(e);
//
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(SignUP_UI.this,t.getMessage(),Toast.LENGTH_LONG).show();
//            }
//        });