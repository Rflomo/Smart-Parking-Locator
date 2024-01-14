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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stripe.model.Customer;

public class Create_Account extends AppCompatActivity {

    TextView tv_account;
    EditText email,password,confirm,phone,name;
    Button btn_register;
    ProgressDialog progressDiolog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        btn_register =findViewById(R.id.buttn_register);
        email=findViewById(R.id.edit_email);
        password=findViewById(R.id.edit_password);
        confirm=findViewById(R.id.edit_confirmpassword);
        tv_account=findViewById(R.id.text_account);
        phone =findViewById(R.id.edit_phone);
        name =findViewById(R.id.edit_name);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressDiolog=new ProgressDialog(this);
        mAuth= FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        tv_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Create_Account.this,loginpage.class));
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performAuth();
            }
        });
    }

    private void performAuth() {
        String email1 =email.getText().toString();
        String pass1=password.getText().toString();
        String confirm1=confirm.getText().toString();
        String phones = phone.getText().toString();
        String names = name.getText().toString();

        if(pass1.isEmpty() || pass1.length() <6)
        {
            password.setError("Enter min 6 char password");
        }
        else if(!pass1.equals(confirm1))
        {
            confirm.setError("Both password don't match");
        }
        else
        {
            progressDiolog.setMessage("Please wait while registration.........");
            progressDiolog.setTitle("Registration");
            progressDiolog.setCanceledOnTouchOutside(false);
            progressDiolog.show();

            mAuth.createUserWithEmailAndPassword(email1,pass1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDiolog.dismiss();

                        // Get the user id and create a new User object
                        String name = mAuth.getCurrentUser().getDisplayName();
                        Users users = new Users(names,email1,phones,"Active");

                        // Get the database reference to Users and add the new user
                        firebaseDatabase = FirebaseDatabase.getInstance();
                        databaseReference = firebaseDatabase.getReference("User_Accounts");
                        databaseReference.child(names).setValue(users);

                        sendUserToNextActivity();
                        Toast.makeText(Create_Account.this,"Registration Successful",Toast.LENGTH_LONG).show();
                    }
                    else{
                        progressDiolog.dismiss();
                        Toast.makeText(Create_Account.this,""+task.getException(),Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    private void sendUserToNextActivity() {
        Intent i = new Intent(Create_Account.this,loginpage.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

}

