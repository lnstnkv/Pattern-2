package ru.tsu.bank.app.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoreRetrofitService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserRetrofitService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CreditRetrofitService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofitService