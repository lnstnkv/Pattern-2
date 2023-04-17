package ru.tsu.bank.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.tsu.bank.BuildConfig
import ru.tsu.bank.app.di.qualifiers.*
import ru.tsu.data.net.AppAuthenticator
import ru.tsu.data.net.AuthInterceptor
import ru.tsu.data.net.Network
import ru.tsu.data.net.accounts.AccountApi
import ru.tsu.data.net.accounts.socket.HistoryWebSocketClient
import ru.tsu.data.net.auth.AuthApi
import ru.tsu.data.net.auth.UserApi
import ru.tsu.data.net.credit.CreditApi
import ru.tsu.data.net.currencies.CurrencyApi
import ru.tsu.data.net.di.AuthApiProvider
import ru.tsu.data.net.operations.OperationsApi
import ru.tsu.domain.preferences.PreferencesDataSource
import javax.inject.Singleton

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
        authInterceptor: AuthInterceptor,
        authenticator: Authenticator,
    ) = Network.getHttpClient(
        cache = cache,
        interceptors = listOf(authInterceptor),
        isDebug = BuildConfig.DEBUG,
        authenticator = authenticator
    )

    @Singleton
    @Provides
    @UserRetrofitService
    fun provideUserRetrofit(client: OkHttpClient, json: Json) = Network.getRetrofit(
        client = client,
        url = "${BuildConfig.API_IP}:${BuildConfig.PORT_USER_SERVICE}/api/",
        json = json,
    )

    @Singleton
    @Provides
    @OperationsRetrofitService
    fun provideOperationsRetrofit(client: OkHttpClient, json: Json) = Network.getRetrofit(
        client = client,
        url = "${BuildConfig.API_IP}:${BuildConfig.PORT_OPERATIONS_SERVICE}/",
        json = json,
    )

    @Singleton
    @Provides
    @AuthRetrofitService
    fun provideAuthRetrofit(client: OkHttpClient, json: Json) = Network.getRetrofit(
        client = client,
        url = "${BuildConfig.API_IP}:${BuildConfig.PORT_AUTH_SERVICE}/api/",
        json = json,
    ).also { retrofit ->
        provideAuthApi(retrofit).also { authApi ->
            AuthApiProvider.inject(
                authApi
            )
        }
    }

    @Singleton
    @Provides
    @CoreRetrofitService
    fun provideCoreRetrofit(client: OkHttpClient, json: Json) = Network.getRetrofit(
        client = client,
        url = "${BuildConfig.API_IP}:${BuildConfig.PORT_CORE_SERVICE}/",
        json = json,
    )

    @Singleton
    @Provides
    @CreditRetrofitService
    fun provideCreditRetrofit(client: OkHttpClient, json: Json) = Network.getRetrofit(
        client = client,
        url = "${BuildConfig.API_IP}:${BuildConfig.PORT_CREDIT_SERVICE}/api/",
        json = json,
    )

    @Singleton
    @Provides
    fun provideUserApi(@UserRetrofitService retrofit: Retrofit): UserApi = Network.getApi(retrofit)

    @Singleton
    @Provides
    fun provideOperationsApi(@OperationsRetrofitService retrofit: Retrofit): OperationsApi = Network.getApi(retrofit)

    @Singleton
    @Provides
    fun provideAuthApi(@AuthRetrofitService retrofit: Retrofit): AuthApi = Network.getApi(retrofit)

    @Singleton
    @Provides
    fun provideAccountsApi(@CoreRetrofitService retrofit: Retrofit): AccountApi =
        Network.getApi(retrofit)

    @Singleton
    @Provides
    fun provideCurrenciesApi(@CoreRetrofitService retrofit: Retrofit): CurrencyApi =
        Network.getApi(retrofit)

    @Singleton
    @Provides
    fun provideCreditApi(@CreditRetrofitService retrofit: Retrofit): CreditApi =
        Network.getApi(retrofit)

    @Singleton
    @Provides
    fun provideAuthInterceptor(preferences: PreferencesDataSource) = AuthInterceptor(preferences)

    @Singleton
    @Provides
    fun provideAuthenticator(preferences: PreferencesDataSource): Authenticator =
        AppAuthenticator(preferences)

    @Singleton
    @Provides
    fun provideHistoryWebSocketClient(): HistoryWebSocketClient = HistoryWebSocketClient(
        json = Network.appJson,
        url = "${BuildConfig.API_IP}:${BuildConfig.PORT_HISTORY_SOCKET}/"
    )
}
