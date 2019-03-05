package com.example.nadya.homelightcontroller;

public class Lampu {
/*    private long durationOn;
    private int onHour;
    private int onMin;
    private int offHour;
    private int offMin;*/
    private int watt;
/*    private int dimmer;
    private String status;
    private String timerStatus;
    private String pirStatus;*/

    public Lampu(){

    }

    public Lampu(int watt) {
        this.watt = watt;
    }

    public int getWatt() {
        return watt;
    }

    public void setWatt(int watt) {
        this.watt = watt;
    }
}
