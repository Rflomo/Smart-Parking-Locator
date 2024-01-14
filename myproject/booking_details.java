package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stripe.model.Customer;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class booking_details extends AppCompatActivity {

    EditText slot_number;
    TextView slotTitle,mTextViewAmount;
    EditText Vehicle_number,Mobile_no,name,current_start,end_time, Amount_display;
    Button save_details,Amount,buttonCalculate;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        mAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Customer.class.getSimpleName());


        slot_number = findViewById(R.id.slot_number);
        Vehicle_number = findViewById(R.id.Vehicle_number);
        current_start = findViewById(R.id.current_time);
        end_time = findViewById(R.id.end_time);
        Mobile_no = findViewById(R.id.mobile_number);
        save_details = findViewById(R.id.book_btn);
        name = findViewById(R.id.person);
        Amount=findViewById(R.id.Amount);

        mTextViewAmount = findViewById(R.id.text_view_amount);

        // Get a reference to the "Calculate" button
        buttonCalculate = findViewById(R.id.button_calculate);


        save_details.setEnabled(false);
        Amount.setEnabled(false);

        slotTitle = findViewById(R.id.slotName);
        slotTitle.setText(String.format("%s", getIntent().getStringExtra("slotNumber")));
        slot_number.setText(String.format("%s",getIntent().getStringExtra("slotNumber")));


        current_start.setOnClickListener(v-> SelectTime());
        end_time.setOnClickListener(v-> SelectTime2());

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the start time and end time from the input fields
                String startTimeString = current_start.getText().toString().trim();
                String endTimeString = end_time.getText().toString().trim();

                // Parse the start time and end time strings into Date objects
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                Date startTime = null;
                Date endTime = null;
                try {
                    startTime = sdf.parse(startTimeString);
                    endTime = sdf.parse(endTimeString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Calculate the duration in minutes
                long durationMinutes = (endTime.getTime() - startTime.getTime()) / (60 * 1000);

                // Calculate the amount based on the duration
                double amount = (double) (durationMinutes * 20) / 60;


                mTextViewAmount.setText(String.format(Locale.getDefault(), "%.2f", amount)); // display with two decimal places

                Amount.setEnabled(true);
            }
        });
        /*Mobile_no.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    sendVerificationCode();
                }
            }
        });*/

        Amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // create the AlertDialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(booking_details.this);

                String amount = mTextViewAmount.getText().toString();
                builder.setTitle("Enter Credit Card Details " + amount);


                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.credit_card_form, null);
                builder.setView(dialogView);


                EditText cardNumberEditText = dialogView.findViewById(R.id.cardNumberEditText);
                EditText expiryDateEditText = dialogView.findViewById(R.id.expiryDateEditText);
                EditText cvvEditText = dialogView.findViewById(R.id.cvvEditText);

                builder.setPositiveButton("Confirm Payment", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String amountString = amount;
                        double amount = Double.parseDouble(amountString);

                        // Calculate the amounts for child A and B
                        double childA = amount * 0.6;
                        double childB = amount * 0.4;

                        // Get a reference to the Firebase database
                        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Amount");

                        // Store the amount in child A
                        database.child("Company_Earnings").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Long longValue = dataSnapshot.getValue(Long.class);
                                String stringValue = String.valueOf(longValue);
                                //String currentValueString = dataSnapshot.getValue(String.class);
                                double currentValue = Double.parseDouble(stringValue);

                                double newValue = currentValue + childA;
                                database.child("Company_Earnings").setValue(newValue);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle error
                            }
                        });

                        // Store the amount in child B
                        database.child("ServiceProvider_Earnings").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Long longValue = dataSnapshot.getValue(Long.class);
                                String stringValue = String.valueOf(longValue);
                                //String currentValueString = dataSnapshot.getValue(String.class);
                                double currentValue = Double.parseDouble(stringValue);

                                double newValue = currentValue + childB;
                                database.child("ServiceProvider_Earnings").setValue(newValue);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Handle error
                            }
                        });

                        Toast.makeText(booking_details.this,"Payment Succesful",Toast.LENGTH_LONG).show();
                        save_details.setEnabled(true);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //
                    }
                });


                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });



                    //saving the data
        save_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pass = Mobile_no.getText().toString();
                String Veicc = Vehicle_number.getText().toString();

                String regx = "^[A-Z]{2}[\\ -]{0,1}[0-9]{2}[\\ -]{0,1}[A-Z]{1,2}[\\ -]{0,1}[0-9]{4}$";
                Pattern p =Pattern.compile(regx);
                Matcher m = p.matcher(Veicc);



                if(Veicc == null){
                    Toast.makeText(getApplicationContext(),"Invalid number plate",Toast.LENGTH_LONG).show();
                }
                else if(m.matches()){
                    if (pass.isEmpty()) {
                        Toast.makeText(booking_details.this, "Pleas fill the phone number field", Toast.LENGTH_SHORT).show();
                    } else if (pass.length() < 10 || pass.length() > 10) {
                        Toast.makeText(booking_details.this, "please enter 10 digits", Toast.LENGTH_SHORT).show();
                    } else{

                        User user = new User(end_time.getText().toString(), pass,
                                name.getText().toString(), slot_number.getText().toString(),
                                current_start.getText().toString(), Veicc
                        );

                        add(user).addOnSuccessListener(suc -> {
                            Toast.makeText(booking_details.this, "data inserted", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(booking_details.this,BookingSlotActivity.class);
                            startActivity(intent);





                        }).addOnFailureListener(er -> {
                            Toast.makeText(booking_details.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }

                }
                else {
                    Toast.makeText(getApplicationContext(),"Enter Valid number plate",Toast.LENGTH_LONG).show();
                }



            }
        });

}

    public Task<Void> add(User user)
    {
        return databaseReference.child(slot_number.getText().toString()).child(Mobile_no.getText().toString()).setValue(user);
    }

    private void SelectTime(){
        Calendar currentTime = Calendar.getInstance();
        int hours = currentTime.get(Calendar.HOUR_OF_DAY);
        int minutes= currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(booking_details.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hours, int minutes) {
                currentTime.set(Calendar.HOUR_OF_DAY,hours);
                currentTime.set(Calendar.MINUTE,minutes);

                String myformat = "HH:mm:ss";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myformat,Locale.US);
                current_start.setText(dateFormat.format(currentTime.getTime()));

            }
        },hours,minutes,true);
        timePickerDialog.setTitle("Select Start Time");
        timePickerDialog.show();
    }
    private void SelectTime2(){
        Calendar currentTime2 = Calendar.getInstance();
        int hours = currentTime2.get(Calendar.HOUR_OF_DAY);
        int minutes= currentTime2.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(booking_details.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hours, int minutes) {
                currentTime2.set(Calendar.HOUR_OF_DAY,hours);
                currentTime2.set(Calendar.MINUTE,minutes);

                String myformat = "HH:mm:ss";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myformat,Locale.US);
                end_time.setText(dateFormat.format(currentTime2.getTime()));
            }
        },hours,minutes,true);
        timePickerDialog.setTitle("Select End Time");
        timePickerDialog.show();
    }

}
