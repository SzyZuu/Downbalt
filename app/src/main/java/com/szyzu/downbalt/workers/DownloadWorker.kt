package com.szyzu.downbalt.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.szyzu.downbalt.data.DataStoreManager
import java.util.logging.Logger

class DownloadWorker(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {
    private val dataStore = DataStoreManager(appContext)

    override fun doWork(): Result {
        val inputLink = inputData.getString("MEDIA_URL") ?: return Result.failure()
        return Result.success()
    }
}