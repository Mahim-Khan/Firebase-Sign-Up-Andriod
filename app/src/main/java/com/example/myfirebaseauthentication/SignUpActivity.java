package com.example.myfirebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseAuthActionCodeException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText signUpEmailEditTextId,signUpPasswordEditTextId;
    private TextView signInTextViewId;
    private Button signUpButtonId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        this.setTitle("Sign Up Activity");
        progressBar=findViewById(R.id.progressBarId);
        signUpEmailEditTextId=findViewById(R.id.signUPEmailEditTextId);
        signUpPasswordEditTextId=findViewById(R.id.signUpPasswordEditTextId);
        signUpButtonId=findViewById(R.id.signUpButtonId);
        signInTextViewId=findViewById(R.id.signInTextViewId);
        signInTextViewId.setOnClickListener(this);
        signUpButtonId.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signUpButtonId:
                userRegister();
                break;
            case R.id.signInTextViewId:
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;

        }

    }

    private void userRegister() {
        String email= signUpEmailEditTextId.getText().toString().trim();
        String password= signUpPasswordEditTextId.getText().toString().trim();
        if(email.isEmpty()){
            signUpEmailEditTextId.setError("Enter an email address");
            signUpEmailEditTextId.requestFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpEmailEditTextId.setError("Enter a valid address");
            signUpEmailEditTextId.requestFocus();
            return;

        }
        if(password.isEmpty()){
            signUpPasswordEditTextId.setError("Enter a password");
            signUpPasswordEditTextId.requestFocus();
        }
        if(password.length()<8){
            signUpPasswordEditTextId.setError("Minimum length of a password should be 8");
            signUpPasswordEditTextId.requestFocus();
            return;


        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                } else {
                    if(task.getException() instanceof FirebaseAuthActionCodeException){
                        Toast.makeText(getApplicationContext(), "User is already Registered", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                  
                }

            }
        });

    }
}