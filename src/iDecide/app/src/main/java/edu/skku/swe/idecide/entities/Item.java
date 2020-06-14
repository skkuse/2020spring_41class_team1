package edu.skku.swe.idecide.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item implements Serializable {
    String user_key;
    String code;
    String manufacture;
    String name;
    String num;
    String score;
    Hardware hardware;
    Review review;
    List<Vendor> vendors;

    public Item(String user_key, String code, String manufacture, String name, String num, String score, Hardware hardware,
                Review review, List<Vendor> vendors){
        this.user_key = user_key;
        this.code = code;
        this.manufacture = manufacture;
        this.name = name;
        this.num = num;
        this.score = score;
        this.hardware = hardware;
        this.review = review;
        this.vendors = vendors;
    }

    public Item(String user_key, String manufacture, String name, String num, String score){
        this.manufacture = manufacture;
        this.name = name;
        this.num = num;
        this.score = score;
    }
    public Item(String user_key, String manufacture, String name){
        this.user_key = user_key;
        this.manufacture = manufacture;
        this.name = name;
    }
    public Item(String code, String manufacture, String name, String num, String score, Hardware hardware, Review review){
        this.user_key = user_key;
        this.code = code;
        this.manufacture = manufacture;
        this.name = name;
        this.num = num;
        this.score = score;
        this.hardware = hardware;
        this.review = review;
    }

    public Item(Map<String, Object> map) {
        if (map.get("code") != null) this.code = map.get("code").toString();
        else this.code = "";
        if (map.get("manufacture") != null) this.manufacture = map.get("manufacture").toString();
        else this.manufacture = "";
        if (map.get("name") != null) this.name = map.get("name").toString();
        else this.name = "";
        if (map.get("num") != null) this.num = map.get("num").toString();
        else this.num = "";
        if (map.get("score") != null) this.score = map.get("score").toString();
        else this.score = "";

        if (map.get("hardware_weight") != null) {
            this.hardware.weight = ((Long) map.get("hardware_weight")).intValue();
        } else this.hardware.weight = -1;
        if (map.get("hardware_design") != null) {
            this.hardware.design = ((Long) map.get("hardware_design")).intValue();
        } else this.hardware.design = -1;
        if (map.get("hardware_screen") != null) {
            this.hardware.screen = ((Long) map.get("hardware_screen")).intValue();
        } else this.hardware.screen = -1;
        if (map.get("hardware_performance") != null) {
            this.hardware.performance = ((Long) map.get("hardware_performance")).intValue();
        } else this.hardware.performance = -1;
        if (map.get("hardware_graphic") != null) {
            this.hardware.graphic = ((Long) map.get("hardware_graphic")).intValue();
        } else this.hardware.graphic = -1;
        if (map.get("hardware_battery") != null) {
            this.hardware.battery = ((Long) map.get("hardware_battery")).intValue();
        } else this.hardware.battery = -1;


        if (map.get("review_weight") != null) {
            this.review.weight = ((Long) map.get("review_weight")).intValue();
        } else this.review.weight = -1;
        if (map.get("review_design") != null) {
            this.review.design = ((Long) map.get("review_design")).intValue();
        } else this.review.design = -1;
        if (map.get("review_screen") != null) {
            this.review.screen = ((Long) map.get("review_screen")).intValue();
        } else this.review.screen = -1;
        if (map.get("review_performance") != null) {
            this.review.performance = ((Long) map.get("review_performance")).intValue();
        } else this.review.performance = -1;
        if (map.get("review_graphic") != null) {
            this.review.graphic = ((Long) map.get("review_graphic")).intValue();
        } else this.review.graphic = -1;
        if (map.get("review_battery") != null) {
            this.review.battery = ((Long) map.get("review_battery")).intValue();
        } else this.review.battery = -1;
    }

    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("manufacture", manufacture);
        result.put("name", name);
        result.put("num", num);
        result.put("score", score);


        result.put("hardware_weight", hardware.weight);
        result.put("hardware_design", hardware.design);
        result.put("hardware_screen", hardware.screen);
        result.put("hardware_performance", hardware.performance);
        result.put("hardware_graphic", hardware.graphic);
        result.put("hardware_battery", hardware.battery);

        result.put("review_weight", review.weight);
        result.put("review_design", review.design);
        result.put("review_screen", review.screen);
        result.put("review_performance", review.performance);
        result.put("review_graphic", review.graphic);
        result.put("review_battery", review.battery);

        //result.put("vendor", vendors);


        return result;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}