package com.example.myproject;

public class SlotNoInfo {
    public String name, numberPlate;
    public boolean isFull;

    public SlotNoInfo() {
    }

    public SlotNoInfo(String name, boolean isFull) {
        this.name = name;
        this.isFull = isFull;
        this.numberPlate = "NONE";
    }
}
