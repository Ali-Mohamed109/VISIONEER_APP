package com.example.visioneerapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_UI extends AppCompatActivity {
    EditText editTextPassword,editTextEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ui);
         editTextPassword =findViewById(R.id.editTextpassword);
         editTextEmail = findViewById(R.id.editTextemail);
        TextView SignUp_NowTextView = findViewById(R.id.SignUpNowTV);
        Button LoginBT = findViewById(R.id.LoginButton);
        TextView forget_password = findViewById(R.id.ForgottenPassword);

        String linkText = "<a href='https://vgineer2.onrender.com/password/forgot-password'>ForgottenPassword?</a>";

        forget_password.setText(Html.fromHtml(linkText));
        forget_password.setMovementMethod(LinkMovementMethod.getInstance());


        LoginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

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
                if (password.length()<8) {
                    editTextPassword.setError("Password should be at least 8 character");
                    editTextPassword.requestFocus();
                    return;
                }

                LoginRequest loginRequest = new LoginRequest();
                loginRequest.setEmail(email);
                loginRequest.setPassword(password);
                loginUser(loginRequest);

                if (!email.equals(loginRequest.getEmail())) {
                    editTextEmail.setError("Email incorrect");
                    editTextEmail.requestFocus();
                    return;
                }
                if (!password.equals(loginRequest.getPassword())) {
                    editTextPassword.setError("Password incorrect");
                    editTextPassword.requestFocus();
                    return;
                }
            }
        });


        SignUp_NowTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),SignUP_UI.class);
                startActivity(intent);
            }
        });
    }
    public void loginUser(LoginRequest loginRequest){

        Call<LoginResponse>loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()){

                    LoginResponse loginResponse = response.body();
                    startActivity(new Intent(Login_UI.this,Bottom_Navi_UI.class).putExtra("data",loginResponse));
                    finish();
                }
                else {

                    String message ="An error occurred Email or Password incorrect ...";
                    Toast.makeText(Login_UI.this,message,Toast.LENGTH_LONG).show();


                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                String message =t.getLocalizedMessage();
                Toast.makeText(Login_UI.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}