package edu.skku.swe.idecide.entities;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class User {
    String nickname = "";
    int gender = -1;
    int age = -1;
    String email = "";

    public User(String nickname, int gender, int age, String email){
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    public User(String nickname, int gender, int age) {
        this.nickname = nickname;
        this.gender = gender;
        this.age = age;
    }

    public User() { }

    public User(Map<String, Object> map) {
        if (map.get("email") != null) this.email = map.get("email").toString();
        else this.email = "";
        if (map.get("nickname") != null) this.nickname = map.get("nickname").toString();
        else this.nickname = "";
        if (map.get("gender") != null) this.gender = ((Long) map.get("gender")).intValue();
        else this.gender = -1;
        if (map.get("age") != null) this.age = ((Long) map.get("age")).intValue();
        else this.age = -1;
    }

    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("nickname", nickname);
        result.put("gender", gender);
        result.put("age", age);

        return result;
    }

    public String getNickname() { return this.nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }
    public int getGender() { return this.gender; }
    public void setGender(int gender) { this.gender = gender; }
    public int getAge() { return this.age; }
    public void setAge(int age) { this.age = age; }
    public String getEmail() { return this.email; }
    public void setEmail(String email) { this.email = email; }
}