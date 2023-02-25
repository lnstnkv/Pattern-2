package ru.tsu.bank.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.tsu.bank.BuildConfig
import javax.inject.Singleton
import ru.tsu.data.net.Network
import ru.tsu.data.net.auth.AuthApi

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkhttpCache() = Network.okHttpCache

    @Singleton
    @Provides
    fun provideJson() = Network.appJson

    @Singleton
    @Provides
    fun provideHttpClient(
        cache: Cache,
    ) = Network.getHttpClient(
        cache = cache,
        interceptors = emptyList(),
        isDebug = BuildConfig.DEBUG,
    )

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient, json: Json) = Network.getRetrofit(
        client = client,
        url = "http://10.0.2.2:8080/api/",
        json = json,
    )

    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit):AuthApi = Network.getApi(retrofit)
}