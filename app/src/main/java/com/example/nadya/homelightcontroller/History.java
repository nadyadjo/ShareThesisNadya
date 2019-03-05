package com.example.nadya.homelightcontroller;

public class History {
    private long duration1;
    private long duration2;
    private long duration3;
    private long duration4;
    private String month;

    public History(){

    }

    public History(long duration1, long duration2, long duration3, long duration4, String month) {
        this.duration1 = duration1;
        this.duration2 = duration2;
        this.duration3 = duration3;
        this.duration4 = duration4;
        this.month = month;
    }

    public long getDuration1() {
        return duration1;
    }

    public void setDuration1(long duration1) {
        this.duration1 = duration1;
    }

    public long getDuration2() {
        return duration2;
    }

    public void setDuration2(long duration2) {
        this.duration2 = duration2;
    }

    public long getDuration3() {
        return duration3;
    }

    public void setDuration3(long duration3) {
        this.duration3 = duration3;
    }

    public long getDuration4() {
        return duration4;
    }

    public void setDuration4(long duration4) {
        this.duration4 = duration4;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
