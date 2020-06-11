package edu.skku.swe.idecide.entities;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//Home card view 에 들어갈 데이터들
//하나의 카드뷰를 클릭하면, 해당 카드뷰 항목에 해당되는 노트북들이 recyclerview로
//나타날 것.
public class HomeCard {
    int cardImage;          //카드뷰 하나에 배경으로 사용될 이미지.
    List<Item> itemList;
    String topic;      //카드뷰에 나타날 글자

    //getter setter
    public int getCardImage() { return cardImage;}
    public void setCardImage(int cardImage) { this.cardImage = cardImage; }
    public String getTopic() {return topic;}
    public void setTopic(String topic) { this.topic = topic; }
    public List<Item> getItemList() { return  itemList; }
    public void setItemList(List<Item> itemList) { this.itemList = itemList; }


    //constructor
    public HomeCard(String topic){
        this.topic = topic;
    }
    public HomeCard(int image, String topic, List<Item> itemList){
        this.cardImage= image;
        this.topic = topic;
        this.itemList = itemList;
    }

    /** Todo: homecard recyclerview안에 아이템(노트북) 들어가야 함
     *  HomeCardAdapter: HomeCard 하나의 adapter
     *  HomeStackAdapter: 전체적인 homecard들의 adapter
     */
    /*
    public static ArrayList<HomeCard>createItemsList(int num_home) {
        ArrayList<HomeCard> homeCardList = new ArrayList<HomeCard>();

        for (int i = 0; i < num_home; i++) {
            homeCardList.add(new HomeCard("SSD" +
                    ":128"));
            Log.d("msg","Home card list string: "+ i+ homeCardList.get(i).topic);

        }
        return homeCardList;
    }//creatItemsList

     */
}
