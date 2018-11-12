package com.example.abuosama.easyclick.Interfacee;

import com.example.abuosama.easyclick.MSG;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Abu Osama on 22-01-2018.
 */

public interface APIService {

    @FormUrlEncoded
    @POST("/dashboard/api/signup.php")
    Call<MSG> userSignUp(@Field("fname") String name,
                         @Field("emailid") String email,
                         @Field("upassword") String password,
                         @Field("sname") String Last,
                         @Field("dob") String dob,
                         @Field("gender") String gender,
                         @Field("userRole") String userrole,
                         @Field("phone") String phone);

    @FormUrlEncoded
    @POST("/dashboard/api/login.php")
    Call<MSG> userLogIn(@Field("username") String email,
                        @Field("upassword") String password);
}
