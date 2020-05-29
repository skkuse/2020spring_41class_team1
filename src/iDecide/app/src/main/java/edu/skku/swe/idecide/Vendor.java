package edu.skku.swe.idecide;

import java.text.DecimalFormat;

// 원래는 price, shipping, siteLink, name이 있어야 함!
public class Vendor {
    int siteImg;
    String price;
    String shipping;
    String siteLink;

    public Vendor(int siteImg, int price, int shipping, String siteLink){
        DecimalFormat formatter = new DecimalFormat("###,###");
        this.siteImg = siteImg;
        this.price = formatter.format(price) + "원";
        if (shipping == 0) this.shipping = "무료배송";
        else this.shipping = formatter.format(shipping) + "원";
        this.siteLink = siteLink;
    }

    public int getSiteImg() {
        return siteImg;
    }
    public void setSiteImg(int image) {
        this.siteImg = siteImg;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(int image) {
        this.price = price;
    }
    public String getShipping() {
        return shipping;
    }
    public void setShipping(int image) {
        this.shipping = shipping;
    }
    public String getSiteLink() {
        return siteLink;
    }
    public void setSiteLink(String siteLink) {
        this.siteLink = siteLink;
    }

}
