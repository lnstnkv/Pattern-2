package ru.tsu.bank.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tsu.domain.account.AccountHistoryDataSource
import ru.tsu.domain.account.AccountInteractor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {

    @Singleton
    @Provides
    fun provideHistoryInteractor(dataSource: AccountHistoryDataSource) = AccountInteractor(dataSource)
}
