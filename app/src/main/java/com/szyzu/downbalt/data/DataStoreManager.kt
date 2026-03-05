package com.szyzu.downbalt.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreManager(val context: Context) {
    companion object{
        val COBALT_LINK = stringPreferencesKey("cobalt_link")
    }

    suspend fun saveToDataStore(linkValue: String){
        context.dataStore.edit {
            it[COBALT_LINK] = linkValue
        }
    }

    suspend fun getFromDataStore(): String?{
        val values = context.dataStore.data.first()
        val value = values[COBALT_LINK]
        return value
    }
}