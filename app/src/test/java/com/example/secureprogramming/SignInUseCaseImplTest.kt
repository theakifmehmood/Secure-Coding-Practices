package com.example.secureprogramming

import com.example.secureprogramming.feature.authentication.data.model.UserDataModel
import com.example.secureprogramming.feature.authentication.domain.model.UserDomainModel
import com.example.secureprogramming.feature.authentication.domain.model.UserSignInRequestDomainModel
import com.example.secureprogramming.feature.authentication.domain.repository.AuthenticationRepository
import com.example.secureprogramming.feature.authentication.domain.usercases.SignInUseCaseImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SignInUseCaseImplTest {
    private val userRepository = mockk<AuthenticationRepository>()
    private val signInUseCase = SignInUseCaseImpl(userRepository)

    @Test
    fun `sign in with valid credentials returns user domain model`() = runBlocking {
        // Arrange
        val userSignInRequestDomainModel = UserSignInRequestDomainModel("john.doe@example.com", "password")
        val userDataModel = UserDataModel("token","John Doe","john.doe@example.com")
        val userDomainModel = UserDomainModel("token","John Doe","john.doe@example.com")
        val expectedUserDomainModel = UserDomainModel("token","John Doe","john.doe@example.com")
        coEvery { userRepository.signIn(userSignInRequestDomainModel) } returns kotlin.Result.success(userDomainModel)

        // Act
        val result = signInUseCase.signIn(userSignInRequestDomainModel)

        // Assert
        assertTrue(result.isSuccess)
        assertEquals(expectedUserDomainModel, result.getOrNull())
    }

    @Test
    fun `sign in with invalid credentials returns error result`() = runBlocking {
        // Arrange
        val userSignInRequestDomainModel = UserSignInRequestDomainModel("john.doe@example.com", "password")
        val expectedErrorMessage = "Invalid credentials"
        coEvery { userRepository.signIn(userSignInRequestDomainModel) } returns kotlin.Result.failure(
            Throwable(expectedErrorMessage))

        // Act
        val result = signInUseCase.signIn(userSignInRequestDomainModel)

        // Assert
        assertTrue(result.isFailure)
        assertEquals(expectedErrorMessage, result.exceptionOrNull()?.message)
    }
}
