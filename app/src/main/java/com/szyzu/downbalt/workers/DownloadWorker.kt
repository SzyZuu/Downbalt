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
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

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

            val publicDownloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val appFolder = File(publicDownloads, appName)
            if(!appFolder.exists()) appFolder.mkdirs()

            val destinationFile = File(appFolder, response.filename)

            val client = OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .build()

            val downloadRequest = Request.Builder()
                .url(inputLink)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
                .addHeader("Accept", "*/*")
                .build()

            client.newCall(downloadRequest).execute().use{downloadResponse ->
                if(!downloadResponse.isSuccessful){
                    Logger.getLogger("DownloadWorker").log(Level.SEVERE, "Download failed")
                    return Result.failure()
                }

                val body = downloadResponse.body
                body.byteStream().use{inputStream ->
                    FileOutputStream(destinationFile).use { outputStream ->
                        inputStream.copyTo(outputStream)
                        outputStream.flush()
                    }
                }
            }

            Result.success()
        }catch (e: Exception){
            Logger.getLogger("DownloadWorker").log(Level.SEVERE, e.message)
            Looper.prepare()
            Toast.makeText(applicationContext, "Download Error :C", Toast.LENGTH_SHORT).show()
            Result.failure()
        }
    }
}