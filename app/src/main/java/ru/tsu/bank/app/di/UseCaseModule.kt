package ru.tsu.bank.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tsu.domain.authorization.AuthUseCase
import ru.tsu.domain.authorization.AuthUseCaseImp
import ru.tsu.domain.authorization.RegistrationUseCase
import ru.tsu.domain.authorization.RegistrationUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun provideRegistrationUseCase(useCase: RegistrationUseCaseImpl):RegistrationUseCase

    @Binds
    abstract fun provideAuthUseCase(useCase: AuthUseCaseImp): AuthUseCase
}