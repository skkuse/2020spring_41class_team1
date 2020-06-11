package edu.skku.swe.idecide.entities;

import java.util.List;

//Home card view 에 들어갈 데이터들
//하나의 카드뷰를 클릭하면, 해당 카드뷰 항목에 해당되는 노트북들이 recyclerview로
//나타날 것.
public class HomeCard {
    int cardBackground;          //카드뷰 하나에 배경으로 사용될 이미지.
    List<Item> itemList;
    String topic;      //카드뷰에 나타날 글자

    //getter setter
    public int getCardBackground() { return cardBackground;}
    public void setCardBackground(int cardBackground) { this.cardBackground = cardBackground; }
    public String getTopic() {return topic;}
    public void setTopic(String topic) { this.topic = topic; }
    public List<Item> getItemList() { return  itemList; }
    public void setItemList(List<Item> itemList) { this.itemList = itemList; }


    //constructor
    public HomeCard(String topic){
        this.topic = topic;
    }
    public HomeCard(int image, String topic, List<Item> itemList){
        this.cardBackground = image;
        this.topic = topic;
        this.itemList = itemList;
    }
}
