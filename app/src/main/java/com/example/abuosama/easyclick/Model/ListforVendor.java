package com.example.abuosama.easyclick.Model;

/**
 * Created by Abu Osama on 27-03-2017.
 */

public class ListforVendor {

    String hallname,hallnumber,hallemail,halladd,hallprice,hallimage,halllatitude,halllongitude,discount;


    public ListforVendor(String hallname, String hallnumber, String price,String actualprice) {
        this.hallname = hallname;
        this.hallnumber = hallnumber;
        this.hallemail = hallemail;
        this.halladd = actualprice;
        this.hallprice = price;
        this.hallimage = hallimage;
        this.halllatitude = halllatitude;
        this.halllongitude = halllongitude;
        this.discount = discount;
    }

    public String getHalllatitude() {
        return halllatitude;
    }

    public void setHalllatitude(String halllatitude) {
        this.halllatitude = halllatitude;
    }

    public String getHalllongitude() {
        return halllongitude;
    }

    public void setHalllongitude(String halllongitude) {
        this.halllongitude = halllongitude;
    }

    public String getHallname() {
        return hallname;
    }

    public void setHallname(String hallname) {
        this.hallname = hallname;
    }

    public String getHallnumber() {
        return hallnumber;
    }

    public void setHallnumber(String hallnumber) {
        this.hallnumber = hallnumber;
    }

    public String getHallemail() {
        return hallemail;
    }

    public void setHallemail(String hallemail) {
        this.hallemail = hallemail;
    }

    public String getHalladd() {
        return halladd;
    }

    public void setHalladd(String halladd) {
        this.halladd = halladd;
    }

    public String getHallprice() {
        return hallprice;
    }

    public void setHallprice(String hallprice) {
        this.hallprice = hallprice;
    }

    public String getHallimage() {
        return hallimage;
    }

    public void setHallimage(String hallimage) {
        this.hallimage = hallimage;

    }

    public String getdiscount() {
        return discount;

    }

    public void setdiscount(String discount) {
        this.discount = discount;
    }
}
