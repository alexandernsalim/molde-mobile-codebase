package com.molde.molde.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val token: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("Authorization", token)
                    .build()
            )
        }
    }

}