package com.example.secureprogramming

import com.example.secureprogramming.feature.authentication.data.mapper.UserDataToDomainMapper
import com.example.secureprogramming.feature.authentication.data.mapper.UserSignInRequestDomainToDataMapper
import com.example.secureprogramming.feature.authentication.data.mapper.UserSignUpRequestDomainToDataMapper
import com.example.secureprogramming.feature.authentication.data.model.UserDataModel
import com.example.secureprogramming.feature.authentication.data.model.UserSignInRequestDataModel
import com.example.secureprogramming.feature.authentication.data.model.UserSignUpRequestDataModel
import com.example.secureprogramming.feature.authentication.data.repository.AuthenticationDataRepository
import com.example.secureprogramming.feature.authentication.data.repository.datasource.AuthenticationSource
import com.example.secureprogramming.feature.authentication.domain.model.UserDomainModel
import com.example.secureprogramming.feature.authentication.domain.model.UserSignInRequestDomainModel
import com.example.secureprogramming.feature.authentication.domain.model.UserSignUpRequestDomainModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AuthenticationDataRepositoryTest {

    private lateinit var repository: AuthenticationDataRepository
    private lateinit var authenticationSource: AuthenticationSource
    private lateinit var userSignInRequestDomainToDataMapper: UserSignInRequestDomainToDataMapper
    private lateinit var userSignUpRequestDomainToDataMapper: UserSignUpRequestDomainToDataMapper
    private lateinit var userModelDataToDomainMapper: UserDataToDomainMapper

    @Before
    fun setUp() {
        authenticationSource = mockk()
        userSignInRequestDomainToDataMapper = mockk()
        userSignUpRequestDomainToDataMapper = mockk()
        userModelDataToDomainMapper = mockk()
        repository = AuthenticationDataRepository(
            authenticationSource,
            userSignInRequestDomainToDataMapper,
            userSignUpRequestDomainToDataMapper,
            userModelDataToDomainMapper
        )
    }

    @Test
    fun `test signIn with valid data`() = runBlocking {
        // Given
        val userDataModel = UserDataModel("john.doe@example.com", "John Doe")
        val userDataModelResult: Result<UserDataModel> = Result.success(userDataModel)
        val userDomainModel = UserDomainModel("john.doe@example.com", "John Doe")
        val userSignInRequestDomainModel = UserSignInRequestDomainModel("john.doe@example.com", "password")
        val userSignInRequestDataModel = UserSignInRequestDataModel("john.doe@example.com", "password")

        coEvery { userSignInRequestDomainToDataMapper.mapDomainToData(userSignInRequestDomainModel) } returns userSignInRequestDataModel
        coEvery { authenticationSource.signIn(userSignInRequestDataModel) } returns userDataModelResult
        coEvery { userModelDataToDomainMapper.mapDomainToData(userDataModelResult) } returns Result.success(userDomainModel)

        // When
        val result = repository.signIn(userSignInRequestDomainModel)

        // Then
        coVerify { authenticationSource.signIn(userSignInRequestDataModel) }
        coVerify { userModelDataToDomainMapper.mapDomainToData(userDataModelResult) }
        assertEquals(Result.success(userDomainModel), result)
    }

    @Test
    fun `test signUp with valid data`() = runBlocking {
        // Given
        val userDataModel = UserDataModel("john.doe@example.com", "John Doe")
        val userDomainModel = UserDomainModel("john.doe@example.com", "John Doe")
        val userDataModelResult: Result<UserDataModel> = Result.success(userDataModel)
        val userSignUpRequestDomainModel = UserSignUpRequestDomainModel("john.doe@example.com", "password")
        val userSignUpRequestDataModel = UserSignUpRequestDataModel("john.doe@example.com", "password")

        coEvery { userSignUpRequestDomainToDataMapper.mapDomainToData(userSignUpRequestDomainModel) } returns userSignUpRequestDataModel
        coEvery { authenticationSource.signUp(userSignUpRequestDataModel) } returns userDataModelResult
        coEvery { userModelDataToDomainMapper.mapDomainToData(userDataModelResult) } returns Result.success(userDomainModel)

        // When
        val result = repository.signUp(userSignUpRequestDomainModel)

        // Then
        coVerify { authenticationSource.signUp(userSignUpRequestDataModel) }
        coVerify { userModelDataToDomainMapper.mapDomainToData(userDataModelResult) }
        assertEquals(Result.success(userDomainModel), result)
    }

    @Test
    fun `test logout`() = runBlocking {
        // When
        repository.logout()

        // Then
        coVerify { authenticationSource.logout() }
    }

    @Test
    fun `test generateEncryptionKey`() {
        // Given
        val password = "password"

        // When
        repository.generateEncryptionKey(password)

        // Then
        verify { authenticationSource.generateEncryptionKey(password) }
    }
}
