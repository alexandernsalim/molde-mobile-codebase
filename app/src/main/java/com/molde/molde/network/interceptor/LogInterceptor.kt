package com.molde.molde.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LogInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.e("URL", chain.request().url().url().toExternalForm())

        return chain.proceed(chain.request())
    }

}