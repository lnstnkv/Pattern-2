package ru.tsu.data.net

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.tsu.domain.preferences.PreferencesDataSource

class AuthInterceptor(private val preferences: PreferencesDataSource) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = preferences.readAuthData()?.accessToken

        val baseRequest = chain.request()
        val port = baseRequest.url.port
        val request: Request = baseRequest
            .newBuilder()
            .apply {

                addHeader("Accept", "*/*")
                addHeader("Content-Type", "application/json")
                // addHeader("Accept-Encoding","gzip, deflate, br")
                if (port != 8000 && port != 8001) {
                    if (!accessToken.isNullOrBlank()) addHeader(HEADER_AUTH, "Bearer $accessToken")
                }
            }
            .build()

        return chain.proceed(request)
    }

    companion object {
        const val HEADER_AUTH = "Authorization"
    }
}

