package com.example.workmanager.worker

import android.net.Uri
import com.example.workmanager.data.remote.ImgurApiService
import com.example.workmanager.domain.models.UploadResponse
import com.example.workmanager.util.Resource
import java.io.File

interface ImgurUploader {
    val imgurApi: ImgurApiService
    suspend fun uploadFile(uri: Uri, title: String? = null): Resource<UploadResponse>
    fun copyStreamToFile(uri: Uri): File
}