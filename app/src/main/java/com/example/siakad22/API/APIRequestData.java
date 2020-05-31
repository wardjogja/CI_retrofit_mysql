package com.example.siakad22.API;

import com.example.siakad22.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface APIRequestData {
    @GET("index.php")
    Call<ResponseModel> ardRetrieveData();

    @FormUrlEncoded
    @POST("index.php")
    Call<ResponseModel> ardCreateData(
      @Field("nama") String nama,
      @Field("nomor") String nomor,
      @Field("status") String status
    );


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "index.php", hasBody = true)
    Call<ResponseModel> ardDeleteData(@Field("id") int id);



    @GET("index.php?id={id}")
    Call<ResponseModel> ardRetrieveDataId(@Path("id") int id);



    @FormUrlEncoded
    @HTTP(method = "PUT", path = "index.php", hasBody = true)
    Call<ResponseModel> ardUpdateData(
            @Field("nama") String nama,
            @Field("nomor") String nomor,
            @Field("status") String status,
            @Field("id") String id
    );

}
