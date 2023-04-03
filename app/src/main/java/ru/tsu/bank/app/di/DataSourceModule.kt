package ru.tsu.bank.app.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tsu.data.db.history.HistoryDao
import ru.tsu.data.db.history.HistoryDataSourceImpl
import ru.tsu.data.net.UserDataSourceImpl
import ru.tsu.data.net.accounts.AccountApi
import ru.tsu.data.net.accounts.AccountDataSourceImpl
import ru.tsu.data.net.auth.AuthApi
import ru.tsu.data.net.auth.AuthDataSourceImpl
import ru.tsu.data.net.auth.UserApi
import ru.tsu.data.net.credit.CreditApi
import ru.tsu.data.net.credit.CreditDataSourceImpl
import ru.tsu.data.net.currencies.CurrencyApi
import ru.tsu.data.net.currencies.CurrencyDataSourceImpl
import ru.tsu.data.preferences.PreferencesDataSourceImpl
import ru.tsu.domain.account.AccountsDataSource
import ru.tsu.domain.account.HistoryDataSource
import ru.tsu.domain.authorization.AuthDataSource
import ru.tsu.domain.authorization.UserDataSource
import ru.tsu.domain.credits.CreditDataSource
import ru.tsu.domain.currency.CurrencyDataSource
import ru.tsu.domain.preferences.PreferencesDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideAuthDataSource(authApi: AuthApi, userApi: UserApi, accountApi: AccountApi): AuthDataSource =
        AuthDataSourceImpl(authApi,userApi, accountApi)

    @Singleton
    @Provides
    fun provideAccountsDataSource(api: AccountApi): AccountsDataSource = AccountDataSourceImpl(api)

    @Singleton
    @Provides
    fun provideCurrencyDataSource(api: CurrencyApi): CurrencyDataSource = CurrencyDataSourceImpl(api)

    @Singleton
    @Provides
    fun provideCreditDataSource(api: CreditApi): CreditDataSource = CreditDataSourceImpl(api)

    @Singleton
    @Provides
    fun providePreferencesDataSource(preferences: SharedPreferences): PreferencesDataSource =
        PreferencesDataSourceImpl(preferences)

    @Singleton
    @Provides
    fun provideHistoryDataSource(dao: HistoryDao): HistoryDataSource = HistoryDataSourceImpl(dao)

    @Singleton
    @Provides
    fun provideUserDataSource(userApi: UserApi): UserDataSource =
        UserDataSourceImpl(userApi)
}
