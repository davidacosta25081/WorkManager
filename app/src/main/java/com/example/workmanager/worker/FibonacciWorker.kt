package com.example.workmanager.worker

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.workmanager.util.Constants.FIB_NUM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class FibonacciWorker(context: Context, workerParameters: WorkerParameters): CoroutineWorker(context,workerParameters) {
    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val fib = fib(5)
        delay(5000L)
        val outputData = workDataOf(FIB_NUM to fib)
        Log.i("FIBONACCI","$fib")
        Result.Success(outputData)
    }

    private fun fib(i: Int): Int {
        if (i <=2){
            return 1
        }
        return fib(i-1) + fib(i-2)
    }
}