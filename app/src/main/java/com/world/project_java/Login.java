package com.world.project_java;

import static com.world.project_java.R.id.*;
import static com.world.project_java.R.id.email;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    TextInputEditText emailText,passwordText;
    Button Login;
    TextView textView;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    String TAG = "Login Page";

    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent = new Intent(Login.this,MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailText = findViewById(email);
        passwordText = findViewById(password);
        Login = findViewById(logBtn);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(tV_log);
        mAuth = FirebaseAuth.getInstance();
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);

            }
        });
       Login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               progressBar.setVisibility(View.VISIBLE);
               String emailValue,passwordValue;
               emailValue = String.valueOf(emailText.getText());
               passwordValue = String.valueOf(passwordText.getText());
               if(TextUtils.isEmpty(emailValue)) {
                   Toast.makeText(Login.this, "Please Enter email", Toast.LENGTH_SHORT).show();
                   return;
               }
               if(TextUtils.isEmpty((passwordValue))) {
                   Toast.makeText(Login.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                   return;
               }
               mAuth.signInWithEmailAndPassword(emailValue, passwordValue)
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               progressBar.setVisibility(View.GONE);
                               if (task.isSuccessful()) {
                                   // Sign in success, update UI with the signed-in user's information
                                   Log.d(TAG, "signInWithEmail:success");
                                   Toast.makeText(Login.this, "Login Successfully",
                                           Toast.LENGTH_SHORT).show();

                                   Intent intent = new Intent(Login.this,MainActivity.class);
                                   startActivity(intent);
                                   finish();

                               } else {
                                   // If sign in fails, display a message to the user.
                                   Log.w(TAG, "signInWithEmail:failure", task.getException());
                                   Toast.makeText(Login.this, "Authentication failed.",
                                           Toast.LENGTH_SHORT).show();

                               }
                           }

                       });
           }
       });


    }
}