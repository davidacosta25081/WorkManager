package com.example.workmanager.domain.models

data class UploadResponse(
    val data: Data,
    val status: Int,
    val success: Boolean
)