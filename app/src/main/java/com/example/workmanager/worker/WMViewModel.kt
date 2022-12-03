package com.example.workmanager.worker

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.example.workmanager.util.Constants


class WMViewModel : ViewModel() {


    fun workers(context: Context, selectedImageUri: Uri?) {
        val imageData = workDataOf(Constants.KEY_IMAGE_URI to selectedImageUri.toString())
        val uploadWorkRequest = OneTimeWorkRequestBuilder<ImgurWorker>()
            .setInputData(imageData)
            .build()
        val fibWorker = OneTimeWorkRequest.from(FibonacciWorker::class.java)
        WorkManager.getInstance(context)
            .beginUniqueWork(
                Constants.IMAGE_UPLOADER, ExistingWorkPolicy.REPLACE, uploadWorkRequest
            ).then(fibWorker).enqueue()
    }
}