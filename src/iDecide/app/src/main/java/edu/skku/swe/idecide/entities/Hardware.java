package edu.skku.swe.idecide.entities;

import java.util.HashMap;
import java.util.Map;

public class Hardware {
    int weight;
    int design;
    int screen;
    int performance;
    int graphic;
    int battery;

    public Hardware(int weight, int design, int screen, int performance, int graphic, int battery){
        this.weight = weight;
        this.design = design;
        this.screen = screen;
        this.performance = performance;
        this.graphic = graphic;
        this.battery = battery;
    }

    public Hardware(Map<String, Object> map) {
        if (map.get("weight") != null) this.weight = ((Long) map.get("weight")).intValue();
        else this.weight = -1;
        if (map.get("design") != null) this.design = ((Long) map.get("design")).intValue();
        else this.design = -1;
        if (map.get("screen") != null) this.screen = ((Long) map.get("screen")).intValue();
        else this.screen = -1;
        if (map.get("performance") != null) this.performance = ((Long) map.get("performance")).intValue();
        else this.performance = -1;
        if (map.get("graphic") != null) this.graphic = ((Long) map.get("graphic")).intValue();
        else this.graphic = -1;
        if (map.get("battery") != null) this.battery = ((Long) map.get("battery")).intValue();
        else this.battery = -1;
    }

    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();
        result.put("weight", weight);
        result.put("design", design);
        result.put("screen", screen);
        result.put("performance", performance);
        result.put("graphic", graphic);
        result.put("battery", battery);

        return result;
    }

    public int getWeight() {
        return weight;
    }

    public int getDesign() {
        return design;
    }

    public int getScreen() {
        return screen;
    }

    public int getPerformance() {
        return performance;
    }

    public int getGraphic() {
        return graphic;
    }

    public int getBattery() {
        return battery;
    }
}