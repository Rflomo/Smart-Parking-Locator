package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity_otp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_otp);

        EditText inputMobile = findViewById(R.id.input_Mobile);
        Button getOTP = findViewById(R.id.btn_getoTP);
        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MainActivity_otp.this, "Enter Mobile number", Toast.LENGTH_LONG).show();
                    return;
                }

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + inputMobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        MainActivity_otp.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential PhoneAuthCreditial) {

                                getOTP.setVisibility(view.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                getOTP.setVisibility(View.VISIBLE);
                                Toast.makeText(MainActivity_otp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                getOTP.setVisibility(View.VISIBLE);
                                Intent i = new Intent(MainActivity_otp.this, MainActivity_verify.class);
                                i.putExtra("mobile", inputMobile.getText().toString());
                                i.putExtra("verificationID", verificationID);
                                startActivity(i);
                            }
                        });
            }
        });
    }
}