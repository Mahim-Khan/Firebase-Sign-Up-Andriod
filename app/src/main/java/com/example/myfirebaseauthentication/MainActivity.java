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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText signInEmailEditTextId,signInPasswordEditTextId;
    private TextView signUpTextViewId;
    private Button signInButtonId;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Sign In Activity");
        mAuth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBarId);
        signInEmailEditTextId=findViewById(R.id.signInEmailEditTextId);
        signInPasswordEditTextId=findViewById(R.id.signInPasswordEditTextId);
        signInButtonId=findViewById(R.id.signInButtonId);
        signUpTextViewId=findViewById(R.id.signUpTextViewId);
        signUpTextViewId.setOnClickListener(this);
        signInButtonId.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signInButtonId:
                userLogin();
                break;
            case R.id.signUpTextViewId:
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
                break;

        }

    }

    private void userLogin() {
        String email= signInEmailEditTextId.getText().toString().trim();
        String password= signInPasswordEditTextId.getText().toString().trim();
        if(email.isEmpty()){
            signInEmailEditTextId.setError("Enter an email address");
            signInEmailEditTextId.requestFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signInEmailEditTextId.setError("Enter a valid address");
            signInEmailEditTextId.requestFocus();
            return;

        }
        if(password.isEmpty()){
            signInPasswordEditTextId.setError("Enter a password");
            signInPasswordEditTextId.requestFocus();
        }
        if(password.length()<8){
            signInPasswordEditTextId.setError("Minimum length of a password should be 8");
            signInPasswordEditTextId.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful())
                {
                    finish();
                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}