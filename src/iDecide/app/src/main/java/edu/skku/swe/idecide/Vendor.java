package edu.skku.swe.idecide;

import java.text.DecimalFormat;

public class Vendor {
    int siteImg;
    String price;
    String shipping;

    public Vendor(int siteImg, int price, int shipping){
        DecimalFormat formatter = new DecimalFormat("###,###");
        this.siteImg = siteImg;
        this.price = formatter.format(price) + "원";
        this.shipping = formatter.format(shipping) + "원";
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

}
