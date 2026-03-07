package com.szyzu.downbalt.workers

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.Looper
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.szyzu.downbalt.data.CobaltRequestBody
import com.szyzu.downbalt.data.DataStoreManager
import com.szyzu.downbalt.data.RetrofitClient
import java.util.logging.Level
import java.util.logging.Logger
import androidx.core.net.toUri
import kotlinx.coroutines.delay

class DownloadWorker(appContext: Context, workerParams: WorkerParameters): CoroutineWorker(appContext, workerParams) {
    private val dataStore = DataStoreManager(appContext)

    override suspend fun doWork(): Result {
        val inputLink = inputData.getString("MEDIA_URL") ?: return Result.failure()
        val appName = inputData.getString("APP_NAME") ?: ""

        return try {
            val apiLink = dataStore.getFromDataStore() ?: return Result.failure()

            Logger.getLogger("DownloadWorker").log(Level.INFO, apiLink)

            RetrofitClient.setLink(apiLink)
            val response = RetrofitClient.apiService.postLink(
                CobaltRequestBody(inputLink)
            )

            val downloadUri = response.url
            Logger.getLogger("DownloadWorker").log(Level.INFO, downloadUri)

            val request = DownloadManager.Request(downloadUri.toUri())
                .addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:148.0) Gecko/20100101 Firefox/148.0")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setAllowedOverMetered(true)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    response.filename
                )

            val downloadManager = applicationContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            downloadManager.enqueue(request)

            Result.success()
        }catch (e: Exception){
            Logger.getLogger("DownloadWorker").log(Level.SEVERE, e.message)
            Looper.prepare()
            Toast.makeText(applicationContext, "Download Error :C", Toast.LENGTH_SHORT).show()
            Result.failure()
        }
    }
}