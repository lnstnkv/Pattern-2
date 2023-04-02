package ru.tsu.domain.preferences

import ru.tsu.domain.authorization.model.AuthData

interface PreferencesDataSource {
    fun saveAuthData(data: AuthData)
    fun readAuthData(): AuthData?
    fun clearAuthData()
}
