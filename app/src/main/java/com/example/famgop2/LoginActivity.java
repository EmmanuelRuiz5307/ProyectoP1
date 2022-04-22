package com.example.famgop2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //Atributos
    Button signIn;
    EditText email , password;
    TextView signUp;
    ProgressBar progressBar;
    //Creacion de objeto Firebase auth
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Instancias
        signIn = findViewById(R.id.login_btn);
        email  = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        signUp = findViewById(R.id.sign_up);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        //Instancia del Objeto FIREBASE
        auth = FirebaseAuth.getInstance();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this , RegistrationActivity.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Uso del metodo loginUser()
                loginUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });


    }

    private void loginUser() {
        String user_email = email.getText().toString();
        String user_pass = password.getText().toString();
        if(TextUtils.isEmpty(user_email)){
            Toast.makeText(this, "Email is Empty", Toast.LENGTH_SHORT).show();
            return ;
        }
        if(TextUtils.isEmpty(user_pass)){
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_SHORT).show();
            return ;
        }
        if(user_pass.length() < 6){
            Toast.makeText(this, "Password will return 6 caracteres ", Toast.LENGTH_SHORT).show();
            return ;
        }
        auth.signInWithEmailAndPassword(user_email , user_pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Login Sucessfull", Toast.LENGTH_SHORT).show();
                } else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Error " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }//Fin del loginUser




}//Fin de la clase