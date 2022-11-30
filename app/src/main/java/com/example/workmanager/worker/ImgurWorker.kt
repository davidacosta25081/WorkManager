package com.example.workmanager.worker

import android.content.Context
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.workmanager.data.remote.ImgurApiService
import com.example.workmanager.domain.models.UploadResponse
import com.example.workmanager.util.Resource
import java.io.File

class ImgurWorker(context: Context, workerParameters: WorkerParameters): CoroutineWorker(context,workerParameters),ImgurUploader {
    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }

    override val imgurApi: ImgurApiService = ImgurApiService.getInstance()

    override suspend fun uploadFile(uri: Uri, title: String?): Resource<UploadResponse> {
        TODO("Not yet implemented")
    }

    override fun copyStreamToFile(uri: Uri): File {
        TODO("Not yet implemented")
    }
}