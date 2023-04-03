package ru.tsu.data.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import ru.tsu.domain.authorization.model.AuthData
import ru.tsu.domain.preferences.PreferencesDataSource

class PreferencesDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
) : PreferencesDataSource {

    override fun saveAuthData(data: AuthData) {
        sharedPreferences.edit {
            putString(ACCESS_TOKEN, data.accessToken)
            putString(REFRESH_TOKEN, data.refreshToken)
        }
    }

    override fun readAuthData(): AuthData? {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, "")
        val refreshToken = sharedPreferences.getString(REFRESH_TOKEN, "")
        val expiresIn = sharedPreferences.getString(EXPIRES_IN, "")
        val refreshExpiresIn = sharedPreferences.getString(REFRESH_EXPIRES_IN, "")
        val tokenType = sharedPreferences.getString(TOKEN_TYPE, "")
        val notBeforePolicy = sharedPreferences.getString(NOT_BEFORE_POLICY, "")
        val sessionState = sharedPreferences.getString(SESSION_STATE, "")
        val scope = sharedPreferences.getString(SCOPE, "")


        return if (!accessToken.isNullOrBlank() && !refreshToken.isNullOrBlank()) {
            AuthData(
                accessToken = accessToken,
                refreshToken = refreshToken,
                expiresIn = expiresIn.orEmpty().toInt(),
                refreshTokenExpiresIn= refreshExpiresIn.orEmpty().toInt(),
                tokenType= tokenType.orEmpty(),
                policy= notBeforePolicy.orEmpty().toInt(),
                sessionState = sessionState.orEmpty(),
                scope = scope.orEmpty(),


            )
        } else {
            null
        }
    }

    override fun clearAuthData() {
        sharedPreferences.edit {
            remove(ACCESS_TOKEN)
            remove(REFRESH_TOKEN)
        }
    }

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val EXPIRES_IN = "expires_in"
        private const val REFRESH_EXPIRES_IN = "refresh_expires_in"
        private const val TOKEN_TYPE = "token_type"
        private const val NOT_BEFORE_POLICY = "not-before-policy"
        private const val SESSION_STATE = "session_state"
        private const val SCOPE = "scope"

    }
}

