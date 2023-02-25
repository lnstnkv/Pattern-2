package ru.tsu.data.net

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.TimeUnit


object Network {

    val okHttpCache: Cache
        get() {
            val cacheDirectory = File("http-cache.tmp")
            val cacheSize = 50 * 1024 * 1024
            return Cache(cacheDirectory, cacheSize.toLong())
        }

    val appJson: Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        prettyPrint = true
    }

    fun getRetrofit(client: OkHttpClient, url: String, json: Json): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(url)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    fun getHttpClient(
        cache: Cache,
        interceptors: List<Interceptor>,
        isDebug: Boolean,
    ): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder().apply {
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            cache(cache)
            interceptors.forEach { addInterceptor(it) }
            if (isDebug) {
                val logLevel = HttpLoggingInterceptor.Level.BODY
                addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))
            }
        }
        return httpClientBuilder.build()
    }

    inline fun <reified T> getApi(retrofit: Retrofit): T = retrofit.create(T::class.java)
}