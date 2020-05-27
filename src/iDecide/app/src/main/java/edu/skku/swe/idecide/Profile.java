package edu.skku.swe.idecide;

import android.graphics.Bitmap;

public class Profile {
    Bitmap image;
    String nickname;
    int gender;
    int age;

    public Profile(Bitmap image, String nickname, int gender, int age){
        this.image = image;
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
    }

    public Bitmap getImage() { return this.image; }
    public void setImage(Bitmap image) { this.image = image; }
    public String getNickname() { return this.nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public int getGender() { return this.gender; }
    public void setGender(Bitmap image) { this.gender = gender; }
    public int getAge() { return this.age; }
    public void setAge(String nickname) { this.age = age; }
}