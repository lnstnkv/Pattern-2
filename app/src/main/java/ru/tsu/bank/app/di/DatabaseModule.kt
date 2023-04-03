package ru.tsu.bank.app.di

import android.content.Context
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.tsu.data.db.AppDatabase
import ru.tsu.data.db.Database
import javax.inject.Singleton

class DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context) = Database.build(context)

    @Singleton
    @Provides
    fun provideSessionDao(database: AppDatabase) = database.historyDao()
}
