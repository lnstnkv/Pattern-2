package ru.tsu.bank.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.tsu.data.db.AppDatabase
import ru.tsu.data.db.Database
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) = Database.build(context)

    @Singleton
    @Provides
    fun provideSessionDao(database: AppDatabase) = database.historyDao()
}
