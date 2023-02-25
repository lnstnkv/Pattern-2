package ru.tsu.bank.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tsu.data.net.auth.AuthApi
import ru.tsu.data.net.auth.AuthDataSourceImpl
import ru.tsu.domain.authorization.AuthDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideAuthDataSource(authApi: AuthApi):AuthDataSource=AuthDataSourceImpl(authApi)
}