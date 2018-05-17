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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import static android.view.View.GONE;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ClassifyAuthentication";

    // START declare_auth
    private FirebaseAuth mAuth;
    // END declare_auth

    EditText editTextEmail, editTextPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // check if user is logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // if logged in
        if(user != null){
            // start main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        // if user is signed out
        else {
            Log.i(TAG, "onAuthStateChange:signed:out");
        }


        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progress_bar);
        mAuth = FirebaseAuth.getInstance();

        // user already registered
        findViewById(R.id.txt_sign_up_link).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    private void userLogin(){
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid address");
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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(GONE);
                // user successfully logged in
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    // clear the intent stack so the user wont be able to return to the login screen via the back button
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else {
                    // check if user does not exist and alert the user
                    if(task.getException() instanceof FirebaseAuthInvalidUserException){
                        Toast.makeText(getApplicationContext(),"Account not registered", Toast.LENGTH_SHORT).show();
                    }
                    // check if bad password;
                    if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();
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
            case R.id.txt_sign_up_link:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                userLogin();
                break;
        }
    }
}

