package com.example.abuosama.easyclick.Interfacee;



import com.example.abuosama.easyclick.Model.Example;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by abu on 12/27/2016.
 */
public interface Interface {

    @Multipart
    @POST("/dashboard/api/survey-data.php")
            void uploadImage(

            @Part("name")
                    String PATH_VAR_USER_ID,
            @Part("dob")
                    String PATH_VAR_USER_ID2,

            @Part("fathers_name")
                    String PATH_VAR_USER_ID1,

            @Part("religion")
                    String PATH_VAR_USER_ID4,

            @Part("tribe")
                    String PATH_VAR_USER_ID5,

            @Part("occupation")
                    String PATH_VAR_USER_ID6,

            @Part("govt_emp")
                    String PATH_VAR_USER_ID7,

            @Part("married")
                    String PATH_VAR_USER_ID8,
            @Part("gender")
                    String PATH_VAR_USER_ID9,

            @Part("armed_force")
                    String PATH_VAR_USER_ID10,

            @Part("education")
                    String PATH_VAR_USER_ID11,

            @Part("house_no")
                    String PATH_VAR_USER_ID12,

            @Part("state")
                    String PATH_VAR_USER_ID13,

            @Part("sub_division")
                    String PATH_VAR_USER_ID14,

            @Part("district")
                    String PATH_VAR_USER_ID15,

            @Part("contact_no")
                    String PATH_VAR_USER_ID16,

            @Part("adhaar_card_no")
                    String PATH_VAR_USER_ID17,

            @Part("voter_id")
                    String PATH_VAR_USER_ID18,

            @Part("profilePic")
                    TypedFile PATH_VAR_FILE,

            @Part("username")
                    String PATH_VAR_AUTHENTICATE_KEY,

            @Part("locality_after")
                    String PATH_,
            @Part(("uid")) String uid,

            Callback<Example> response);

//   public void uploadImage(int id, String name, String dob, String fname, String religo,
//                           String tribd, String occ, String govt, String mrried, String gend,
//                           String armedforcesd, String educationd, String housenod, String stated,
//                           String subdivisiond, String districtd, String contactnod, String aadharcardd,
//                           String voteridd, String proficeficd, String username, String locality, Callback<Example> callback);
//




}
