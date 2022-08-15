package com.application.services.partn.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.application.services.partn.databinding.EgyptSunActivityBinding
import com.application.services.partn.utils.Consts
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("sharedPref")

class EgyptSunActivity : AppCompatActivity() {

    private var _binding: EgyptSunActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = EgyptSunActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.IO) {
            val adId =
                AdvertisingIdClient.getAdvertisingIdInfo(applicationContext).id.toString()
            OneSignal.initWithContext(applicationContext)
            OneSignal.setAppId(Consts.ONESIGNAL_ID)
            OneSignal.setExternalUserId(adId)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}