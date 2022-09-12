package com.coshield.codingbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    TextView alreadyhaveacc;
    EditText email_box,pass_box,confirm_pass;
    Button btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        email_box = findViewById(R.id.email_box);
        pass_box = findViewById(R.id.pass_box);
        confirm_pass = findViewById(R.id.confirm_pass);


        alreadyhaveacc = findViewById(R.id.alreadyhaveacc);
        alreadyhaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent al = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(al);

            }
        });

        btnRegister = findViewById(R.id.btn_Register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            PerforAuth();
            }
        });

    }


    private void PerforAuth() {

        String email = email_box.getText().toString();
        String password = pass_box.getText().toString();
        String confrimPassword = confirm_pass.getText().toString();
        if (!email.matches(emailPattern))
        {
            email_box.setError("Enter Email is Invalid");
            email_box.requestFocus();

        } else if(password.isEmpty() || password.length()<6)
        {
            pass_box.setError("Enter Password Correctly");
        }else if(!password.equals(confrimPassword))
        {
            confirm_pass.setError("Password Not Match To Field");
        }else
        {

           progressDialog.setMessage("Please Wait While Registration...");
           progressDialog.setTitle("Registration");
           progressDialog.setCanceledOnTouchOutside(false);
           progressDialog.show();

           mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {

                   if (task.isSuccessful())

                   {
                   }else
                   {
                       progressDialog.dismiss();
                       Toast.makeText(RegisterActivity.this, "+task.getException(),", Toast.LENGTH_SHORT).show();
                   }
               }
           });


        }



    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegisterActivity.this,BookActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}