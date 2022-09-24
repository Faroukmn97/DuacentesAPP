package com.example.duacentes.interfaces;

import com.example.duacentes.models.SendImageModel;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @Multipart
    @POST("fileupload/uploaduser")
    Call<SendImageModel> updateimage(@Part MultipartBody.Part image);

}
