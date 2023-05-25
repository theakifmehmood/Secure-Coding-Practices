package com.example.secureprogramming.di.authentication

import com.example.secureprogramming.feature.crud_password.presentation.mapper.PasswordDomainToPresentationMapper
import com.example.secureprogramming.feature.crud_password.presentation.mapper.PasswordPresentationToDomainMapper
import com.example.secureprogramming.feature.authentication.presentation.mapper.UserDomainToPresentationMapper
import com.example.secureprogramming.feature.home.presentation.mapper.PasswordDomainToPresentationListMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationPresentationModule {

    @Provides
    @Singleton
    fun providersUserModelDomainToPresentationMapper(): UserDomainToPresentationMapper = UserDomainToPresentationMapper()


    @Provides
    @Singleton
    fun providersPasswordDomainToPresentationMapper(): PasswordPresentationToDomainMapper = PasswordPresentationToDomainMapper()

    @Provides
    @Singleton
    fun providersPasswordPresentationToDomainMapper(): PasswordDomainToPresentationMapper = PasswordDomainToPresentationMapper()

    @Provides
    @Singleton
    fun providersPasswordDomainToPresentationListMapper(): PasswordDomainToPresentationListMapper = PasswordDomainToPresentationListMapper()

}