package edu.skku.swe.idecide.entities;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

// 원래는 price, shipping, siteLink, name이 있어야 함!
public class Vendor implements Serializable {
    String name;
    String price;
    String shipping;
    String siteLink;

    public Vendor(String name, int price, int shipping, String siteLink){
        DecimalFormat formatter = new DecimalFormat("###,###");
        this.name = name;
        this.price = formatter.format(price) + "원";
        if (shipping == 0) this.shipping = "무료배송";
        else this.shipping = formatter.format(shipping) + "원";
        this.siteLink = siteLink;
    }

    public Vendor(String name, String price, String shipping, String siteLink){
        this.name = name;
        this.price = price;
        this.shipping = shipping;
        this.siteLink = siteLink;
    }

    public Vendor(Map<String, Object> map) {
        if (map.get("name") != null) this.name = map.get("name").toString();
        else this.name = "";
        if (map.get("price") != null) this.price = map.get("price").toString();
        else this.price = "";
        if (map.get("shipping") != null) this.shipping = map.get("shipping").toString();
        else this.shipping = "";
        if (map.get("siteLink") != null) this.siteLink = map.get("siteLink").toString();
        else this.siteLink = "";
    }

    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result = new HashMap<>();

        result.put("name", name);
        result.put("price", price);
        result.put("shipping", shipping);
        result.put("siteLink", siteLink);

        return result;
    }



    public String getName() {
        return name;
    }
    public void setName(int image) {
        this.name = name;
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
