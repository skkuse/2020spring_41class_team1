package edu.skku.swe.idecide.entities;

public class Laptop {
    public String getmLaptopName() {
        return mLaptopName;
    }

    public void setmLaptopName(String mLaptopName) {
        this.mLaptopName = mLaptopName;
    }

    public String getmMinPrice() {
        return mMinPrice;
    }

    public void setmMinPrice(String mMinPrice) {
        this.mMinPrice = mMinPrice;
    }

    public String getmMaxBatteryLife() {
        return mMaxBatteryLife;
    }

    public void setmMaxBatteryLife(String mMaxBatteryLife) {
        this.mMaxBatteryLife = mMaxBatteryLife;
    }

    public String getmDisplaySize() {
        return mDisplaySize;
    }

    public void setmDisplaySize(String mDisplaySize) {
        this.mDisplaySize = mDisplaySize;
    }

    public String getmOfficialSite() {
        return mOfficialSite;
    }

    public void setmOfficialSite(String mOfficialSite) {
        this.mOfficialSite = mOfficialSite;
    }

    public String getmScore() {
        return mScore;
    }

    public void setmScore(String mScore) {
        this.mScore = mScore;
    }

    public String getmCpuScore() {
        return mCpuScore;
    }

    public void setmCpuScore(String mCpuScore) {
        this.mCpuScore = mCpuScore;
    }

    public String getmGpuScore() {
        return mGpuScore;
    }

    public void setmGpuScore(String mGpuScore) {
        this.mGpuScore = mGpuScore;
    }

    private String mMaxBatteryLife;
    private String mDisplaySize;
    private String mOfficialSite;
    private String mScore;
    private String mCpuScore;
    private String mGpuScore;
    private String mLaptopName;
    private String mMinPrice;
    public Laptop(){}
}
