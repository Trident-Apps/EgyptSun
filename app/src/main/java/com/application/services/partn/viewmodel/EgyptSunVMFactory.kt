package com.application.services.partn.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EgyptSunVMFactory(private val app: Application) :
    ViewModelProvider.AndroidViewModelFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EgyptSunVM(app) as T
    }
}