package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myproject.databinding.ActivityUpdateUserdataBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class updateUserdata extends AppCompatActivity {
    ActivityUpdateUserdataBinding binding;
    DatabaseReference databaseReference;
    String user,veichcle,startduration,endduration,mobilel,slotnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateUserdataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user=getIntent().getStringExtra("name");
        veichcle=getIntent().getStringExtra("Vehicle_number");
        startduration=getIntent().getStringExtra("current_time");
        endduration=getIntent().getStringExtra("end_time");
        mobilel=getIntent().getStringExtra("Mobile_no");
        slotnum=getIntent().getStringExtra("slot_number");

        binding.UPperson.setText(user);
        binding.UPVehiclenumber.setText(veichcle);
        binding.UPcurrenttime.setText(startduration);
        binding.UPendtime.setText(endduration);
        binding.UPmobilenumber.setText(mobilel);
        binding.UPslotnumber.setText(slotnum);

        binding.UPcurrenttime.setOnClickListener(v-> SelectTime());
        binding.UPendtime.setOnClickListener(v-> SelectTime2());

        binding.UPdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String slot_Number = binding.UPslotnumber.getText().toString();
                String veichle_number = binding.UPVehiclenumber.getText().toString();
                String starting_duration = binding.UPcurrenttime.getText().toString();
                String ending_time = binding.UPendtime.getText().toString();
                String mobile = binding.UPmobilenumber.getText().toString();
                String name = binding.UPperson.getText().toString();

                updatedata(ending_time, mobile, name, slot_Number, starting_duration, veichle_number);

            }

        });
    }

    private void updatedata(String ending_time, String mobile, String name, String slot_Number, String starting_duration, String veichle_number) {

        HashMap User = new HashMap();
        User.put("ending_time",ending_time);
        User.put("mobile",mobile);
        User.put("name",name);
        User.put("slot_Number",slot_Number);
        User.put("starting_duration",starting_duration);
        User.put("veichle_number",veichle_number);

        databaseReference = FirebaseDatabase.getInstance().getReference("Customer").child(slot_Number);
        databaseReference.child(mobile).updateChildren(User).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()){

                    binding.UPslotnumber.setText("");
                    binding.UPVehiclenumber.setText("");
                    binding.UPcurrenttime.setText("");
                    binding.UPendtime.setText("");
                    binding.UPmobilenumber.setText("");
                    binding.UPperson.setText("");

                    Toast.makeText(updateUserdata.this,"Successfully Updated",Toast.LENGTH_SHORT).show();

                }else {

                    Toast.makeText(updateUserdata.this,"Failed to Update",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void SelectTime(){
        Calendar currentTime = Calendar.getInstance();
        int hours = currentTime.get(Calendar.HOUR_OF_DAY);
        int minutes= currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(updateUserdata.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hours, int minutes) {
                currentTime.set(Calendar.HOUR_OF_DAY,hours);
                currentTime.set(Calendar.MINUTE,minutes);

                String myformat = "HH:mm:ss";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myformat, Locale.US);
                binding.UPcurrenttime.setText(dateFormat.format(currentTime.getTime()));

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
        timePickerDialog = new TimePickerDialog(updateUserdata.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hours, int minutes) {
                currentTime2.set(Calendar.HOUR_OF_DAY,hours);
                currentTime2.set(Calendar.MINUTE,minutes);

                String myformat = "HH:mm:ss";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myformat,Locale.US);
                binding.UPendtime.setText(dateFormat.format(currentTime2.getTime()));
            }
        },hours,minutes,true);
        timePickerDialog.setTitle("Select End Time");
        timePickerDialog.show();
    }
}