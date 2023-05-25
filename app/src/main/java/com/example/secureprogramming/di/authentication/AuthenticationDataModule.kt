package com.example.secureprogramming.di.authentication

import com.example.secureprogramming.feature.crud_password.data.mapper.PasswordDataToDomainMapper
import com.example.secureprogramming.feature.crud_password.data.mapper.PasswordDomainToDataMapper
import com.example.secureprogramming.feature.crud_password.data.repository.AddPasswordDataRepository
import com.example.secureprogramming.feature.crud_password.data.repository.datasource.AddPasswordDataSource
import com.example.secureprogramming.feature.crud_password.data.repository.datasource.AddPasswordSource
import com.example.secureprogramming.feature.crud_password.domain.repository.AddPasswordRepository
import com.example.secureprogramming.feature.authentication.data.mapper.UserDataToDomainMapper
import com.example.secureprogramming.feature.authentication.data.mapper.UserSignInRequestDomainToDataMapper
import com.example.secureprogramming.feature.authentication.data.mapper.UserSignUpRequestDomainToDataMapper
import com.example.secureprogramming.feature.authentication.data.repository.AuthenticationDataRepository
import com.example.secureprogramming.feature.authentication.data.repository.datasource.AuthenticationDataSource
import com.example.secureprogramming.feature.authentication.data.repository.datasource.AuthenticationSource
import com.example.secureprogramming.feature.authentication.domain.repository.AuthenticationRepository
import com.example.secureprogramming.feature.crud_password.data.repository.datasource.CryptoDataSource
import com.example.secureprogramming.feature.crud_password.data.repository.datasource.CryptoSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationDataModule {

    @Provides
    @Singleton
    fun providersAuthenticationDataRepository(
        authenticationSource: AuthenticationSource,
        userSignInRequestDomainToDataMapper: UserSignInRequestDomainToDataMapper,
        userSignUpRequestDomainToDataMapper: UserSignUpRequestDomainToDataMapper,
        userModelDataToDomainMapper: UserDataToDomainMapper
    ): AuthenticationRepository = AuthenticationDataRepository(
        authenticationSource,
        userSignInRequestDomainToDataMapper,
        userSignUpRequestDomainToDataMapper,
        userModelDataToDomainMapper
    )

    @Provides
    @Singleton
    fun providersAddPasswordDataRepository(
        addPasswordSource: AddPasswordSource,
        passwordDomainToDataMapper: PasswordDomainToDataMapper,
        passwordDataToDomainMapper: PasswordDataToDomainMapper
    ): AddPasswordRepository = AddPasswordDataRepository(
        addPasswordSource,
        passwordDomainToDataMapper,
        passwordDataToDomainMapper,
    )

    @Provides
    fun provideFirebaseDatabaseReference(): FirebaseFirestore {
        return  FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return  FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providersAuthenticationSource(): AuthenticationSource = AuthenticationDataSource()

    @Provides
    @Singleton
    fun providersUserDataToDomainMapper(): UserDataToDomainMapper = UserDataToDomainMapper()

    @Provides
    @Singleton
    fun providersUserSignInRequestDomainToDataMapper(): UserSignInRequestDomainToDataMapper = UserSignInRequestDomainToDataMapper()

    @Provides
    @Singleton
    fun providersUserSignUpRequestDomainToDataMapper(): UserSignUpRequestDomainToDataMapper = UserSignUpRequestDomainToDataMapper()

    @Provides
    @Singleton
    fun providersPasswordDomainToDataMapper(): PasswordDomainToDataMapper = PasswordDomainToDataMapper()

    @Provides
    @Singleton
    fun providersPasswordDataToDomainMapper(): PasswordDataToDomainMapper = PasswordDataToDomainMapper()

    @Provides
    @Singleton
    fun providersCryptoSource(): CryptoSource = CryptoDataSource()

    @Provides
    @Singleton
    fun providersAddPasswordSource(fireStore: FirebaseFirestore, firebaseAuth: FirebaseAuth,  cryptoSource: CryptoSource):
            AddPasswordSource = AddPasswordDataSource(fireStore, firebaseAuth ,cryptoSource)



}