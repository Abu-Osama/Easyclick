package com.example.abuosama.easyclick;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MSG {
 
    private Integer success;
    private String message;
    @SerializedName("utype")
    @Expose
    private String usertype;

    @SerializedName("uid")
    @Expose
    private String uid;

    @SerializedName("first_name")
    @Expose
    private String fname;

    @SerializedName("surname")
    @Expose
    private String sname;

    @SerializedName("gender")
    @Expose
    private String gender;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    /**
     * No args constructor for use in serialization
     */
    public MSG() {
    }
 
    /**
     * @param message
     * @param success
     */
    public MSG(Integer success, String message) {
        super();
        this.success = success;
        this.message = message;
    }
 
    public Integer getSuccess() {
        return success;
    }
 
    public void setSuccess(Integer success) {
        this.success = success;
    }
 
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
 
}