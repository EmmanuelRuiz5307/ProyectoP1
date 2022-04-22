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

import com.example.famgop2.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    //Atributos
    Button signUp;
    EditText email , password , name;
    TextView signIn;
    ProgressBar progressBar;

    //Se crea un atributo de tipo Firebase 
    FirebaseAuth auth;


    //Se crea el objeto del realtime database;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //Instancias
        signUp = findViewById(R.id.login_btn);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
        signIn = findViewById(R.id.sign_in);
        progressBar = findViewById(R.id.progressbar);


        progressBar.setVisibility(View.GONE);
        //Se crea instancia de firebase
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this , LoginActivity.class));
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creacion de un metodo para crear usuarios
                createUser();
                //Se hace visible el progress bar
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }//Fin OnCreate

    private void createUser() { //Inicio createUser
        String user_name = name.getText().toString();
        String user_email = email.getText().toString();
        String user_pass = password.getText().toString();

        if(TextUtils.isEmpty(user_name)){
            Toast.makeText(this, "Name is Empty", Toast.LENGTH_SHORT).show();
            return ;
        }
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

        auth.createUserWithEmailAndPassword(user_email , user_pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            /*
                                Se crea una clase llamada UserModel de la cual se obtendran los parametros que estan
                                en el constructor.En seguida lo que sucede es se obtiene la ID unica del usuario registrado
                                lo cual hace mencion que sabremos que usario esta dentro de la app.
                             */
                            UserModel usermodel = new UserModel(user_name , user_email , user_pass);
                            String id = task.getResult().getUser().getUid();
                            /*
                                Lo que esta sucediendo abajo es que,se crea un nodo llamado "Users" (por asi decirlo una tabla)
                                la cual tendra un hijo "id" el cual recibe los valores del objeto de la clase UserModel.
                             */
                            database.getReference().child("Users").child(id).setValue(usermodel);
                            //Se hace visible el progress bar y se va cuando entra a este metodo
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegistrationActivity.this, "Registration Completed", Toast.LENGTH_SHORT).show();
                        } else{
                            //Se hace visible el progress bar
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegistrationActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    } //Fin createUser
}//Fin de la clase