package com.example.pbdday9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pbdday9.data.ApiClient;
import com.example.pbdday9.data.ApiInterface;
import com.example.pbdday9.data.model.Register;
import com.example.pbdday9.databinding.ActivityLoginBinding;
import com.example.pbdday9.databinding.ActivityRegisterBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.registerToLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.btnRegister.setOnClickListener(view ->{
            String username = binding.registerUsername.getText().toString();
            String name = binding.registerNama.getText().toString();
            String password = binding.registerPassword.getText().toString();
            register(username, name , password);
        });

    }

    private  void register (String username, String name,String password){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Register> call = apiInterface.RegisterResponse(username, name, password);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.isSuccessful()&& response.body() != null){
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    Toast.makeText(RegisterActivity.this, "Akun berhasil di buat", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else {
                    Toast.makeText(RegisterActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Gagal Register: "+ t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}