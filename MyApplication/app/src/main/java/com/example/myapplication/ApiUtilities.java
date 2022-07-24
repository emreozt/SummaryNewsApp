package com.example.myapplication;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilities {


    private static Retrofit retrofit = null; //retrofit object oluşturdum

    public static ApiInterface getApiInterface()
    {
        if (retrofit == null ){ //retrofit objesi yoksa


            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }

}
