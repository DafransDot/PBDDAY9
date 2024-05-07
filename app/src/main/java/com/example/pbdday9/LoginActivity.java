package com.example.pbdday9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pbdday9.data.ApiClient;
import com.example.pbdday9.data.ApiInterface;
import com.example.pbdday9.data.model.Login;
import com.example.pbdday9.data.model.LoginData;
import com.example.pbdday9.databinding.ActivityLoginBinding;
import com.example.pbdday9.ui.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityLoginBinding binding;
    private String Username, Password;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginToRegister.setOnClickListener(this);
        binding.btnlogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loginToRegister){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        } else if (v.getId()== R.id.btnlogin){
            Username = binding.loginUsername.getText().toString();
            Password = binding.loginPassword.getText().toString();
            Login(Username , Password);
        }
    }

    public void Login (String Username, String Password){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Login> call = apiInterface.loginResponse(Username,Password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()&& response.body() != null&&response.body().isStatus()){

                    sessionManager = new SessionManager(LoginActivity.this);
                    LoginData loginData = response.body().getData();
                    sessionManager.createLoginSession(loginData);


                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Toast.makeText(LoginActivity.this, "Anda telah Login",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}