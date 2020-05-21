package com.molde.molde

import android.app.Application
import android.content.Context

class AppComponent : Application() {

    companion object {
        var instance: AppComponent? = null
            private set

        val context: Context?
            get() = instance
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

}