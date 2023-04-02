package ru.tsu.data.net

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.tsu.domain.preferences.PreferencesDataSource

class AuthInterceptor(private val preferences: PreferencesDataSource) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = preferences.readAuthData()?.accessToken

        val baseRequest = chain.request()
        val request: Request = baseRequest
            .newBuilder()
            .apply {
                addHeader("Accept", "application/json")
                addHeader("Content-Type", "application/x-www-form-urlencoded")
                if (!accessToken.isNullOrBlank()) addHeader(HEADER_AUTH, "Bearer $accessToken")
            }
            .build()

        return chain.proceed(request)
    }

    companion object {
        const val HEADER_AUTH = "Authorization"
    }
}

