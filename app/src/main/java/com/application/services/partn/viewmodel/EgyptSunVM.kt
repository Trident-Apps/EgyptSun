package com.application.services.partn.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.application.services.partn.ui.activities.dataStore
import com.application.services.partn.utils.Consts
import com.application.services.partn.utils.OneSignalTag
import com.application.services.partn.utils.UriBuilder
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import kotlinx.coroutines.flow.first

class EgyptSunVM(app: Application) : AndroidViewModel(app) {
    private val uriBuilder = UriBuilder()
    private val oneSignal = OneSignalTag()

    val urlLiveData: MutableLiveData<String> = MutableLiveData()

    fun fetchDeepLink(activity: Activity) {
        AppLinkData.fetchDeferredAppLinkData(activity) {
            val deeplink = it?.targetUri.toString()
            if (deeplink == "null") {
                fetchApsFlyer(activity)
            } else {
                urlLiveData.postValue(uriBuilder.createUrl(deeplink, null, activity))
                oneSignal.sendTag(deeplink, null)
            }
        }
    }

    private fun fetchApsFlyer(activity: Activity) {
        AppsFlyerLib.getInstance().init(Consts.APPS_DEV_KEY, object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
                oneSignal.sendTag("null", p0)
                urlLiveData.postValue(uriBuilder.createUrl("null", p0, activity))
            }

            override fun onConversionDataFail(p0: String?) {
                TODO("Not yet implemented")
            }

            override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {
                TODO("Not yet implemented")
            }

            override fun onAttributionFailure(p0: String?) {
                TODO("Not yet implemented")
            }

        }, activity)
        AppsFlyerLib.getInstance().start(activity)
    }

    suspend fun checkDatastoreValue(key: String, context: Context): String? {
        val datastoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[datastoreKey]
    }

    suspend fun saveLastUrl(url: String, context: Context) {
        val datastoreKey = stringPreferencesKey("finalUrl")
        context.dataStore.edit {
            it[datastoreKey] = url
        }
    }

}