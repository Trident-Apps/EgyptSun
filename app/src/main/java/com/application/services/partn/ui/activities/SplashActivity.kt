package com.application.services.partn.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(Intent(this@SplashActivity, EgyptSunActivity::class.java)) {
            startActivity(this)
            finish()
        }
    }
}