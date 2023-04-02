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

        return if (!accessToken.isNullOrBlank() && !refreshToken.isNullOrBlank()) {
            AuthData(
                accessToken = accessToken,
                refreshToken = refreshToken,
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
    }
}

