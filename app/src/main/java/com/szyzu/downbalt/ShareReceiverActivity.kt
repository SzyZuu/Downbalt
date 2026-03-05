package com.szyzu.downbalt

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ShareCompat
import com.szyzu.downbalt.data.DataStoreManager

class ShareReceiverActivity : Activity() {
    private val _dataStore = DataStoreManager(this@ShareReceiverActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentReader = ShareCompat.IntentReader(this)
        val appName = intentReader.callingApplicationLabel.toString() // will be used to sort in folders depending on app

        if(intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT) // extract url

            if(sharedText != null){
                Toast.makeText(this, "Download started >:D", Toast.LENGTH_SHORT).show()
                handleSendLink(sharedText)
            }
        }

        finish()
    }

    fun handleSendLink(link : String){
        // TODO: send to cobalt api -> download file with download manager
    }
}