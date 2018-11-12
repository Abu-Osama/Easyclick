package com.example.abuosama.easyclick.Model;

/**
 * Created by Abu Osama on 01-05-2017.
 */

public class Config {


    public static final String Login_url = "http://www.makemyhall.com/m/hallcate.php";
   // public  static final String Data_longi="http://www.makemyhall.com/m/marriagehall.php";

    //Tags for my JSON
    public static final String TAG_Hall_ok = "categorys";
    public static final String TAG_Hall_ok1 = "vendersListing";
    public static final String TAG_Hall_Name = "name";
    public static final String TAG_Banner_Name = "banner_name";
    public static final String TAG_Hall_Num = "hall_contect_no";
    public static final String TAG_Address = "hall_address";
    //public static final String TAG_Price = "hall_price";
    public static final String TAG_Banner = "image";
    public static final String TAG_Banner_image = "banner_image";
    public static final String TAG_Price = "visiting_charge";
    public static final String TAG_Price_actual = "offer_charge";


    //for sharedPereferences

    public static final String KEY_EMAIL = "email_id";
    public static final String KEY_PASSWORD = "password";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "0";

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";

    //This would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF = "email";


    public static final String usertype = "usertype";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";






}
