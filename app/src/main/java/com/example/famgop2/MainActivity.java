package com.example.famgop2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();
        /*
        Si ya logeaste con la cuenta, se brinca todos los activitys para que automaticamente
        entres al main principal.
         */
        if(auth.getCurrentUser()!=null){
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Por favor espera...", Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View view) {
        startActivity(new Intent(MainActivity.this , LoginActivity.class));
    }

    public void registration(View view) {
        startActivity(new Intent(MainActivity.this , RegistrationActivity.class));
    }
}