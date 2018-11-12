package com.example.abuosama.easyclick.Interfacee;

import android.content.Context;


public class RestClient extends AbstractRestClient {
    public static final String BASE_URL = "http://sur-as.com/";



    private Interface DataService;

    public RestClient(Context context) {
        super(context, BASE_URL);
    }

    @Override
    public void initApi() {
        DataService = restAdapter.create(Interface.class);
    }


    public Interface getDataService() {
        return DataService;
    }
}
