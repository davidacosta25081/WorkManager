package com.example.workmanager.worker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.workmanager.databinding.ActivityMainBinding
import com.example.workmanager.util.Constants.KEY_IMAGE_URI


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSelectImage.setOnClickListener { imageChooser() }
        binding.btnUploadImage.setOnClickListener {
            selectedImageUri?.let {
                val imageData = workDataOf(KEY_IMAGE_URI to selectedImageUri.toString())
                val uploadWorkRequest = OneTimeWorkRequestBuilder<ImgurWorker>()
                    .setInputData(imageData)
                    .build()
                WorkManager.getInstance(this).enqueue(uploadWorkRequest)
            } ?: Toast.makeText(
                applicationContext,
                "Please... Select image first",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    private fun imageChooser() {
        val i = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 200)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == 200) {
                selectedImageUri = data?.data
                binding.ivImage.setImageURI(selectedImageUri)
            }
        }
    }
}