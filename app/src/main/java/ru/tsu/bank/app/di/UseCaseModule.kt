package ru.tsu.bank.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tsu.domain.account.*
import ru.tsu.domain.account.usecases.*
import ru.tsu.domain.authorization.usecases.*
import ru.tsu.domain.credits.CreateCreditUseCases
import ru.tsu.domain.credits.CreateCreditUseCasesImpl
import ru.tsu.domain.credits.GetCreditsUseCases
import ru.tsu.domain.credits.GetCreditsUseCasesImpl
import ru.tsu.domain.currency.GetCurrenciesUseCase
import ru.tsu.domain.currency.GetCurrenciesUseCaseImpl
import ru.tsu.domain.operations.usecases.*

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun provideRegistrationUseCase(useCase: RegistrationUseCaseImpl): RegistrationUseCase

    @Binds
    abstract fun provideGetUsersUseCase(useCase: GetUsersUseCaseImpl): GetUsersUseCase

    @Binds
    abstract fun provideAuthUseCase(useCase: AuthUseCaseImpl): AuthUseCase

    @Binds
    abstract fun provideGetAccountsUseCase(useCase: GetListAccountUseCaseImpl): GetListAccountUseCase

    @Binds
    abstract fun provideDeleteAccountsUseCase(useCase: DeleteAccountUseCaseImpl): DeleteAccountUseCase

    @Binds
    abstract fun provideGetAccountHistoryUseCase(useCase: GetAccountHistoryUseCaseImpl): GetAccountHistoryUseCase

    @Binds
    abstract fun provideGetAccountUseCase(useCase: GetAccountUseCaseImpl): GetAccountUseCase

    @Binds
    abstract fun provideWithdrawMoneyFromAccountUseCase(useCase: WithdrawMoneyFromAccountUseCaseImpl): WithdrawMoneyFromAccountUseCase

    @Binds
    abstract fun provideTopUpAccountUseCase(useCase: TopUpAccountUseCaseImpl): TopUpAccountUseCase

    @Binds
    abstract fun provideTransferMoneyUseCase(useCase: TransferMoneyUseCaseImpl): TransferMoneyUseCase

    @Binds
    abstract fun provideBlockAccountUseCase(useCase: BlockAccountUseCaseImpl): BlockAccountUseCase

    @Binds
    abstract fun provideUnblockAccountUseCase(useCase: UnblockAccountUseCaseImpl): UnblockAccountUseCase

    @Binds
    abstract fun provideCurrencyUseCase(useCase: GetCurrenciesUseCaseImpl): GetCurrenciesUseCase

    @Binds
    abstract fun provideCreateAccountUseCase(useCase: CreateAccountUseCaseImpl): CreateAccountUseCase

    @Binds
    abstract fun provideCreateCreditUseCase(useCase: CreateCreditUseCasesImpl): CreateCreditUseCases

    @Binds
    abstract fun provideGetCreditUseCase(useCase: GetCreditsUseCasesImpl): GetCreditsUseCases

    @Binds
    abstract fun provideLogoutUseCase(useCase: LogoutUseCaseImpl): LogoutUseCase
}
