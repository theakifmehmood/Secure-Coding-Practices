package com.example.secureprogramming.di.authentication

import com.example.secureprogramming.feature.crud_password.domain.repository.AddPasswordRepository
import com.example.secureprogramming.feature.crud_password.domain.usercases.StorePasswordUseCase
import com.example.secureprogramming.feature.crud_password.domain.usercases.StorePasswordUseCaseImp
import com.example.secureprogramming.feature.authentication.data.repository.AuthenticationDataRepository
import com.example.secureprogramming.feature.authentication.domain.repository.AuthenticationRepository
import com.example.secureprogramming.feature.authentication.domain.usercases.GenerateEncryptionUseCase
import com.example.secureprogramming.feature.authentication.domain.usercases.GenerateEncryptionUseCaseImpl
import com.example.secureprogramming.feature.authentication.domain.usercases.LogoutUseCase
import com.example.secureprogramming.feature.authentication.domain.usercases.LogoutUseCaseImpl
import com.example.secureprogramming.feature.authentication.domain.usercases.SignInUseCase
import com.example.secureprogramming.feature.authentication.domain.usercases.SignInUseCaseImpl
import com.example.secureprogramming.feature.authentication.domain.usercases.SignUpUseCase
import com.example.secureprogramming.feature.authentication.domain.usercases.SignUpUseCaseImpl
import com.example.secureprogramming.feature.crud_password.domain.usercases.RetrievePasswordUseCase
import com.example.secureprogramming.feature.crud_password.domain.usercases.RetrievePasswordUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationDomainModule {

    @Provides
    @Singleton
    fun providersSignInUseCase(
        authenticationRepository: AuthenticationRepository
    ): SignInUseCase = SignInUseCaseImpl(
        authenticationRepository
    )


    @Provides
    @Singleton
    fun providersSignUpUseCase(
        authenticationDataRepository: AuthenticationDataRepository
    ): SignUpUseCase = SignUpUseCaseImpl(
        authenticationDataRepository
    )

    @Provides
    @Singleton
    fun providersStorePasswordUseCase(
        addPasswordRepository: AddPasswordRepository,
    ): StorePasswordUseCase = StorePasswordUseCaseImp(
        addPasswordRepository
    )

    @Provides
    @Singleton
    fun providersRetrievePasswordUseCase(
        addPasswordRepository: AddPasswordRepository,
    ): RetrievePasswordUseCase = RetrievePasswordUseCaseImp(
        addPasswordRepository
    )

    @Provides
    @Singleton
    fun providersLogoutUseCase(
        authenticationDataRepository: AuthenticationDataRepository
    ): LogoutUseCase = LogoutUseCaseImpl(
        authenticationDataRepository
    )

    @Provides
    @Singleton
    fun providersGenerateEncryptionUseCase(
        authenticationDataRepository: AuthenticationDataRepository,
    ): GenerateEncryptionUseCase = GenerateEncryptionUseCaseImpl(
        authenticationDataRepository
    )
}