package com.saswat.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText mFullname,mUsername,mEmail,mPassword,mCopassword;
    Button mregisterbtn;
    TextView mLoginbtn;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mFullname=findViewById(R.id.register_fname);
        mUsername=findViewById(R.id.register_username);
        mEmail=findViewById(R.id.register_email);
        mPassword=findViewById(R.id.register_password);
        mCopassword=findViewById(R.id.register_copassword);
        mregisterbtn=findViewById(R.id.register_btn);
        mLoginbtn=findViewById(R.id.register_loginlink);

        firebaseAuth=FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }




        mregisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String copassword=mCopassword.getText().toString().trim();


                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }

               if(TextUtils.isEmpty(copassword)){
                   mCopassword.setError("It's Required");
                   return;
               }


               if(password.length()<6){
                   mPassword.setError("Password Must be 6 Character Long");
                   return;
               }

               if(!password.equals(copassword)){
                   mCopassword.setError("Passwords are not Matching");
                   return;
               }

               firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this,MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(Register.this, "Coorectly Create the User" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                   }
               });


            }
        });



        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });

















    }
}
