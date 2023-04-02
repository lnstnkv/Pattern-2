package ru.tsu.data.net.di

import ru.tsu.data.net.auth.AuthApi

object AuthApiProvider {

    @Volatile
    private var authApi: AuthApi? = null

    fun inject(authApi: AuthApi) {
        synchronized(this) {
            if (this.authApi != null) return
            this.authApi = authApi
        }
    }

    fun get(): AuthApi = authApi ?: error("No AuthApi provided")
}
