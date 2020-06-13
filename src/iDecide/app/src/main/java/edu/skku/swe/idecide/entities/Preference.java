package edu.skku.swe.idecide.entities;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Preference {
    String date;
    int budget;
    int main_purpose;
    int usage;
    int place_of_use;
    int carry;
    int weight;
    int screen_size;
    int os;
    int brand;
    int ranking;

    public Preference(String date, int budget, int main_purpose, int usage, int place_of_use, int carry,
                      int weight, int screen_size, int os, int brand, int ranking){
        this.date = date;
        this.budget = budget;
        this.main_purpose = main_purpose;
        this.usage = usage;
        this.place_of_use = place_of_use;
        this.carry = carry;
        this.weight = weight;
        this.screen_size = screen_size;
        this.os = os;
        this.brand = brand;
        this.ranking = ranking;
    }

    public Preference(String date) { this.date = date; }
    public Preference() { }


    public Preference(Map<String, Object> map) {
        if (map.get("date") != null) this.date = map.get("date").toString();
        else this.date = "";
        if (map.get("budget") != null) this.budget = ((Long) map.get("budget")).intValue();
        else this.budget = -1;
        if (map.get("main_purpose") != null) this.main_purpose = ((Long) map.get("main_purpose")).intValue();
        else this.main_purpose = -1;
        if (map.get("usage") != null) this.usage = ((Long) map.get("usage")).intValue();
        else this.usage = -1;
        if (map.get("place_of_use") != null) this.place_of_use = ((Long) map.get("place_of_use")).intValue();
        else this.place_of_use = -1;
        if (map.get("carry") != null) this.carry = ((Long) map.get("carry")).intValue();
        else this.carry = -1;
        if (map.get("weight") != null) this.weight = ((Long) map.get("weight")).intValue();
        else this.weight = -1;
        if (map.get("screen_size") != null) this.screen_size = ((Long) map.get("screen_size")).intValue();
        else this.screen_size = -1;
        if (map.get("os") != null) this.os = ((Long) map.get("os")).intValue();
        else this.os = -1;
        if (map.get("brand") != null) this.brand = ((Long) map.get("brand")).intValue();
        else this.brand = -1;
        if (map.get("ranking") != null) this.ranking = ((Long) map.get("ranking")).intValue();
        else this.ranking = -1;
    }

    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("budget", budget);
        result.put("main_purpose", main_purpose);
        result.put("usage", usage);
        result.put("place_of_use", place_of_use);
        result.put("carry", carry);
        result.put("weight", weight);
        result.put("screen_size", screen_size);
        result.put("os", os);
        result.put("brand", brand);
        result.put("ranking", ranking);


        return result;
    }
}