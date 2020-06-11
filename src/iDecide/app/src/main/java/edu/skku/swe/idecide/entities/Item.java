package edu.skku.swe.idecide.entities;

public class Item {
    int image;
    String manufacture;
    String name;
    String num;
    String score;

    public Item(int image, String manufacture, String name, String num, String score){
        this.image = image;
        this.manufacture = manufacture;
        this.name = name;
        this.num = num;
        this.score = score;
    }
    public Item(int image, String manufacture, String name){
        this.image = image;
        this.manufacture = manufacture;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}