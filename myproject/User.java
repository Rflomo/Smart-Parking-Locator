package com.example.myproject;

public class User {
    public String name;
    private String Ending_time,Mobile,Name,Slot_Number,Starting_duration,Veichle_number;



    public User() {
    }

    public User(String ending_time, String mobile, String name, String slot_Number, String starting_duration, String veichle_number) {
        this.Ending_time = ending_time;
        this.Mobile = mobile;
        this.Name = name;
        this.Slot_Number = slot_Number;
        this.Starting_duration = starting_duration;
        this.Veichle_number = veichle_number;

    }


    public String getEnding_time() {
        return Ending_time;
    }

    public void setEnding_time(String ending_time) {
        Ending_time = ending_time;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        this.Mobile = mobile;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getSlot_Number() {
        return Slot_Number;
    }

    public void setSlot_Number(String slot_Number) {
        this.Slot_Number = slot_Number;
    }

    public String getStarting_duration() {
        return Starting_duration;
    }

    public void setStarting_duration(String starting_duration) {
        Starting_duration = starting_duration;
    }

    public String getVeichle_number() {
        return Veichle_number;
    }

    public void setVeichle_number(String veichle_number) {
        this.Veichle_number = veichle_number;
    }

}