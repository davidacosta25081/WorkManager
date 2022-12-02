package com.example.workmanager.data.remote

import com.example.workmanager.domain.models.UploadResponse
import com.example.workmanager.util.Constants.BASE_URL
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImgurApiService {
    @Multipart
    @POST("upload")
    suspend fun uploadImage(
        @Header("Authorization") auth: String = "Client-ID $CLIENT_ID",
        @Part image: MultipartBody.Part?,
    ): Response<UploadResponse>


    companion object {
        const val CLIENT_ID = "52eba355633a632"

        fun getInstance() = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImgurApiService::class.java)

    }
}