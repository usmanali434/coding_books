package com.coshield.codingbooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    TextView create_new_account;
    EditText email_box, pass_box;
    Button btnLogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    private Object NullPointerException;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        create_new_account=findViewById(R.id.create_new_account);
        email_box = findViewById(R.id.email_box);
        pass_box = findViewById(R.id.pass_box);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        btnLogin=findViewById(R.id.btn_login);
        setContentView(R.layout.activity_main);

        

        create_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cr = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(cr);

            }
        });


            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PerforLogin();
                }
            });


    }





    private void PerforLogin() {

        String email = email_box.getText().toString();
        String password = pass_box.getText().toString();
        if (!email.matches(emailPattern)) {
            email_box.setError("Enter Email is Invalid");
            email_box.requestFocus();

        } else if (password.isEmpty() || password.length() < 6) {
            pass_box.setError("Enter Password Correctly");
        } else {

            progressDialog.setMessage("Please Wait While Login...");
            progressDialog.setTitle("LOGIN");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(MainActivity.this, "login Successful", Toast.LENGTH_SHORT).show();

                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "+task.getException(),", Toast.LENGTH_SHORT).show();
                    }


                    }
            });

        }
    }
    private void sendUserToNextActivity() {
        Intent intent = new Intent(MainActivity.this,BookActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}