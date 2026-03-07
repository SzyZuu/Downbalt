package com.szyzu.downbalt

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.szyzu.downbalt.data.DataStoreManager
import com.szyzu.downbalt.ui.MainScreen
import com.szyzu.downbalt.ui.theme.DownbaltTheme

class MainActivity : ComponentActivity() {
    private val _dataStore = DataStoreManager(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DownbaltTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(dataStore = _dataStore)
                    innerPadding
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    DownbaltTheme {
        MainScreen(dataStore = DataStoreManager(MainActivity()))
    }
}