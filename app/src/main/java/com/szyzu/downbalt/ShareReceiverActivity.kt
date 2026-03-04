package com.szyzu.downbalt

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ShareCompat

class ShareReceiverActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentReader = ShareCompat.IntentReader(this)
        val appName = intentReader.callingApplicationLabel.toString() // will be used to sort in folders depending on app


        if(intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
            val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT) // extract url

            if(sharedText != null){
                Toast.makeText(this, "Download started >:D", Toast.LENGTH_SHORT).show()
            }
        }

        finish()
    }
}