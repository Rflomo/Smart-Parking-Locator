package com.example.myproject;

public class Slot {
    private String slotNumber;
    private String slotStatus;

    public Slot() {

    }

    public Slot(String slotNumber, String slotStatus) {
        this.slotNumber = slotNumber;
        this.slotStatus = slotStatus;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getSlotStatus() {
        return slotStatus;
    }

    public void setSlotStatus(String slotStatus) {
        this.slotStatus = slotStatus;
    }
}
