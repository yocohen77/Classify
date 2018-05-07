package com.example.android.classify;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "Classify Registration";
    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // login link
        findViewById(R.id.txt_link_login).setOnClickListener(this);
        // register button
        findViewById(R.id.btn_register).setOnClickListener(this);

        editTextEmail = (EditText)findViewById(R.id.email_reg);
        editTextPassword = (EditText)findViewById(R.id.password_reg);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);

        mAuth = FirebaseAuth.getInstance();

    }
    private void registerUser(){
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email address");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Minimum password length is 6 characters");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        // create the user
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    // if successful, alert the user and send them to main activity
                    Log.d(TAG, "createUserWithEmail:success");
                    Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    // block user from returning to this screen
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                // if user creation was unsuccessful
                else {
                    // check if user already exists and alert the user
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                        Toast.makeText(getApplicationContext(),"User is already registered", Toast.LENGTH_SHORT).show();
                    }
                    // log the error from firebase
                    FirebaseException e = (FirebaseException) task.getException();
                    Log.e(TAG, "createUserWithEmail:unsuccessful", e);
                }
            }
        });
    }
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.btn_register:
                registerUser();
                break;
            case R.id.txt_link_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}
