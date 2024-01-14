package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class loginpage extends AppCompatActivity {

    Button bttn_login,sign_back, forgotPass;
    EditText edit_tx1,edit_tx2;
    String user,pass;
    android.app.ProgressDialog ProgressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        bttn_login=findViewById(R.id.buttn1);
        edit_tx1=findViewById(R.id.edit_Name);
        edit_tx2=findViewById(R.id.edit_Password_login);
        sign_back=findViewById(R.id.signBack);

        forgotPass =findViewById(R.id.forgotPasswordbutton);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ProgressDialog = new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        sign_back.setOnClickListener(view -> {
            Intent i = new Intent(loginpage.this,Create_Account.class);
            startActivity(i);
        });

        bttn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                performLogin();
            }
        });

        forgotPass.setOnClickListener(view -> {
            Intent i = new Intent(loginpage.this,MainActivity.class);
            startActivity(i);
        });

    }

    private void performLogin() {
        user=edit_tx1.getText().toString();
        pass=edit_tx2.getText().toString();

        if (pass.isEmpty() || pass.length() < 6){
            edit_tx2.setError("Enter min 6 char password");
        }

        else {
            ProgressDialog.setMessage("Please wait while login........");
            ProgressDialog.setTitle("Login");
            ProgressDialog.setCanceledOnTouchOutside(false);
            ProgressDialog.show();


            mAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        ProgressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(loginpage.this, "Login Successful", Toast.LENGTH_LONG).show();
                    } else {
                        ProgressDialog.dismiss();
                        Toast.makeText(loginpage.this, "" + task.getException(), Toast.LENGTH_LONG).show();
                    }
                }


            });
        }

    }

    private void sendUserToNextActivity() {


        if(user.equals("servprovider@company.com"))
        {
            Intent intent = new Intent(loginpage.this, serviceProvider.class);
            startActivity(intent);

        }
        else
        {
            Intent intent = new Intent(loginpage.this, Main_userpage.class);
            startActivity(intent);

        }


    }
}