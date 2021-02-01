package com.arish1999.videosio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore database;
    EditText emailBox,passwordBox,nameBox;
    Button loginBtn,signupBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth=FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();

        emailBox=findViewById(R.id.emailBox);
        nameBox=findViewById(R.id.namebox);
        passwordBox=findViewById(R.id.passwordBox);

        loginBtn=findViewById(R.id.loginbtn);
        signupBtn=findViewById(R.id.createbtn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password,name;
                email=emailBox.getText().toString();
                password=passwordBox.getText().toString();
                name=nameBox.getText().toString();

                User user = new User();
                user.setEmail(email);
                user.setName(name);
                user.setPassword(password);

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            database.collection("Users")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                }
                            });
                            //Toast.makeText(SignupActivity.this, "Account is created.", Toast.LENGTH_SHORT).show();
                        }
                        else 
                        {
                            Toast.makeText(SignupActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}