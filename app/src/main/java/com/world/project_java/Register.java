package com.world.project_java;

import static com.world.project_java.R.id.email;
import static com.world.project_java.R.id.logBtn;
import static com.world.project_java.R.id.password;
import static com.world.project_java.R.id.regBtn;

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
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    TextInputEditText emailText,passwordText;
    Button Register;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    String TAG = "Registration Page";
    TextView textView;
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent = new Intent(Register.this,MainActivity.class);
            startActivity(intent);
            finish();
        }


    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailText = findViewById(email);
        passwordText = findViewById(password);
        Register = findViewById(regBtn);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.tV_regc);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
                finish();
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String emailValue,passwordValue;
             emailValue = String.valueOf(emailText.getText());
             passwordValue = String.valueOf(passwordText.getText());
             if(TextUtils.isEmpty(emailValue)) {
                 Toast.makeText(Register.this, "Please Enter email", Toast.LENGTH_SHORT).show();
                 return;
             }
             if(TextUtils.isEmpty((passwordValue))) {
                 Toast.makeText(Register.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                 return;
             }
             // User Creation
             mAuth.createUserWithEmailAndPassword(emailValue,passwordValue).
                     addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             progressBar.setVisibility(View.GONE);
                             if(task.isSuccessful()){
                                 Log.d(TAG,"Successfully created user email");
                                 Toast.makeText(Register.this, "Account Created", Toast.LENGTH_SHORT).show();


                             }
                             else {
                                Log.w(TAG,"Failure in creating user",task.getException());
                                 Toast.makeText(Register.this, "Authentication Failed", Toast.LENGTH_SHORT).show();


                             }
                         }
                     });




            }
        });
    }
}