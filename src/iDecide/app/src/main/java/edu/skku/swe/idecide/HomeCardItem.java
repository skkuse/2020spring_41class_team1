package edu.skku.swe.idecide;

import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
//Home card view 에 들어갈 데이터들
//하나의 카드뷰를 클릭하면, 해당 카드뷰 항목에 해당되는 노트북들이 recyclerview로
//나타날 것.
public class HomeCardItem {
    int cardImage;          //카드뷰 하나에 배경으로 사용될 이미지.
    String preference;      //카드뷰에 나타날 글자

    //getter setter
    public int getCardImage() { return cardImage;}
    public void setCardImage(int cardImage) {this.cardImage = cardImage;}
    public String getPreference() {return preference;}
    public void setPreference(String preference) { this.preference = preference; }


    //constructor
    public HomeCardItem(String preference){
        this.preference= preference;
    }
    public HomeCardItem(int image, String preference){
        this.cardImage= image;
        this.preference= preference;
    }

    public static ArrayList<HomeCardItem>createItemsList(int num_home) {
        ArrayList<HomeCardItem> homeCardList = new ArrayList<HomeCardItem>();

        for (int i = 0; i < num_home; i++) {
            homeCardList.add(new HomeCardItem("SSD" +
                    ":128"));
            Log.d("msg","Home card list string: "+ i+ homeCardList.get(i).preference);

        }
        return homeCardList;
    }//creatItemsList
}//HomeItem
