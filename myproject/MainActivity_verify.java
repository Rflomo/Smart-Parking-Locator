package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity_verify extends AppCompatActivity {

    private EditText inputCode1,inputCode2,inputCode3,inputCode4,inputCode5,inputCode6;
    private String verificationID,mobile;
    Button back_verify,buttonverify;
    TextView tv_resend,textmobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_verify);

            textmobile=findViewById(R.id.textmobile);
            tv_resend=findViewById(R.id.textResendOTP);
            textmobile.setText(String.format(
                    "+91-%s",getIntent().getStringExtra("mobile")
            ));
            inputCode1=findViewById(R.id.otp_1);
            inputCode2=findViewById(R.id.otp_2);
            inputCode3=findViewById(R.id.otp_3);
            inputCode4=findViewById(R.id.otp_4);
            inputCode5=findViewById(R.id.otp_5);
            inputCode6=findViewById(R.id.otp_6);

            buttonverify=findViewById(R.id.verify);
            back_verify=findViewById(R.id.btn_back_verifyOTP);
            setupOTPInputs();
            verificationID=getIntent().getStringExtra("verificationID");
            mobile=getIntent().getStringExtra("mobile");
            buttonverify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(inputCode1.getText().toString().trim().isEmpty()||
                            inputCode2.getText().toString().trim().isEmpty()||
                            inputCode3.getText().toString().trim().isEmpty()||
                            inputCode4.getText().toString().trim().isEmpty()||
                            inputCode5.getText().toString().trim().isEmpty() ||
                            inputCode6.getText().toString().trim().isEmpty()){
                        Toast.makeText(MainActivity_verify.this, "Enter valid code", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String code = inputCode1.getText().toString() + inputCode2.getText().toString() + inputCode3.getText().toString() + inputCode4.getText().toString() +
                            inputCode5.getText().toString() + inputCode6.getText().toString();
                    if(verificationID != null){
                        PhoneAuthCredential PhoneAuthCredential = PhoneAuthProvider.getCredential(
                                verificationID,
                                code
                        );
                        FirebaseAuth.getInstance().signInWithCredential(PhoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){

                                            Intent i = new Intent(MainActivity_verify.this,Main_userpage.class);
                                            i.putExtra("mobile",mobile);
                                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(i);

                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"The verification code entered was wrong",Toast.LENGTH_LONG);
                                        }
                                    }
                                });
                    }
                    else {

                    }
                }
            });
            tv_resend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + mobile,
                            60,
                            TimeUnit.SECONDS,
                            MainActivity_verify.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {

                                    Toast.makeText(MainActivity_verify.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationID,@NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken){
                                    //super.onCodeSent(s,forceResendingToken);
                                }

                            });
                }
            });
            back_verify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MainActivity_verify.this, MainActivity_otp.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            });
        }

    private void setupOTPInputs() {
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputCode2.requestFocus();
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputCode3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputCode4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputCode5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    inputCode6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}