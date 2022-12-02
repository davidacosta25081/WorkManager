package com.example.workmanager.worker

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.workmanager.data.remote.ImgurApiService
import com.example.workmanager.domain.models.UploadResponse
import com.example.workmanager.util.Constants.KEY_IMAGE_URI
import com.example.workmanager.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

class ImgurWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters), ImgurUploader {
    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            val uriImageInput =
                inputData.getString(KEY_IMAGE_URI) ?: return@withContext Result.failure()
            val response = uploadFile(Uri.parse(uriImageInput))
            when (response) {
                is Resource.Error -> {
                    Result.failure()
                }

                is Resource.Success -> {
                   val link = response.data.data.link
                    val outputData = workDataOf(KEY_IMAGE_URI to link)
                    Log.i("LINK", link)
                    Result.Success(outputData)
                }
            }


        } catch (e: Exception) {
            Result.failure()

        }
    }

    override val imgurApi: ImgurApiService = ImgurApiService.getInstance()

    override suspend fun uploadFile(uri: Uri, title: String?): Resource<UploadResponse> {
        return try {
            val file = copyStreamToFile(uri)
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val filePart = MultipartBody.Part.createFormData("image", file.name, requestFile)

            val response = imgurApi.uploadImage(image = filePart)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error("Something went wrong")
            }
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Something went wrong")
        }


    }

    override fun copyStreamToFile(uri: Uri): File {
        val outputFile = File.createTempFile("temp", null)
        applicationContext.contentResolver
            .openInputStream(uri)?.use { input ->
                val outputStream = FileOutputStream(outputFile)
                outputStream.use { output ->
                    val buffer = ByteArray(4 * 1024)
                    while (true) {
                        val byteCount = input.read(buffer)
                        if (byteCount < 0) break
                        output.write(buffer, 0, byteCount)
                    }
                    output.flush()

                }
            }
        return outputFile
    }
}