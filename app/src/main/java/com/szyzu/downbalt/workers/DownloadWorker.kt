package com.szyzu.downbalt.workers

import android.content.Context
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.szyzu.downbalt.data.CobaltRequestBody
import com.szyzu.downbalt.data.DataStoreManager
import com.szyzu.downbalt.data.RetrofitClient
import java.util.logging.Level
import java.util.logging.Logger

class DownloadWorker(appContext: Context, workerParams: WorkerParameters): CoroutineWorker(appContext, workerParams) {
    private val dataStore = DataStoreManager(appContext)

    override suspend fun doWork(): Result {
        val inputLink = inputData.getString("MEDIA_URL") ?: return Result.failure()

        return try {
            val apiLink = dataStore.getFromDataStore() ?: return Result.failure()

            Logger.getLogger("DownloadWorker").log(Level.INFO, apiLink)

            RetrofitClient.setLink(apiLink)
            val response = RetrofitClient.apiService.postLink(
                CobaltRequestBody(inputLink)
            )

            Result.success()
        }catch (e: Exception){
            Logger.getLogger("DownloadWorker").log(Level.SEVERE, e.message)
            Toast.makeText(applicationContext, "Download Error :C", Toast.LENGTH_SHORT).show()
            Result.failure()
        }
    }
}