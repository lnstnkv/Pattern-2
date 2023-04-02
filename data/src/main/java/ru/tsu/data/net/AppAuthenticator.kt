package ru.tsu.data.net

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.tsu.data.net.auth.model.ParamsRefreshTokenDto
import ru.tsu.data.net.di.AuthApiProvider
import ru.tsu.domain.preferences.PreferencesDataSource

class AppAuthenticator(private val preferences: PreferencesDataSource) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? = runBlocking {
        while (true) {
            try {
                AuthApiProvider.get()
                break
            } catch (_: RuntimeException) {
            }
        }
        val api = AuthApiProvider.get()
        val authData = preferences.readAuthData() ?: return@runBlocking null

        val refreshToken = ParamsRefreshTokenDto(authData.refreshToken)
        val newAuthData = api.refreshToken(refreshToken).toDomain()
        preferences.saveAuthData(newAuthData)

        response
            .request
            .newBuilder()
            .addHeader(AuthInterceptor.HEADER_AUTH, "Bearer ${newAuthData.accessToken}")
            .build()
    }
}
