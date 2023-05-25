package com.example.secureprogramming

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.secureprogramming.feature.authentication.domain.usercases.GenerateEncryptionUseCase
import com.example.secureprogramming.feature.authentication.domain.usercases.SignInUseCase
import com.example.secureprogramming.feature.authentication.presentation.SignInViewModel
import com.example.secureprogramming.feature.authentication.presentation.mapper.UserDomainToPresentationMapper
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class SignInViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var signInUseCase: SignInUseCase
    private lateinit var generateEncryptionUseCase: GenerateEncryptionUseCase
    private lateinit var userDomainToPresentationMapper: UserDomainToPresentationMapper
    private lateinit var viewModel: SignInViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        signInUseCase = mockk()
        generateEncryptionUseCase = mockk()
        userDomainToPresentationMapper = mockk()
        viewModel = SignInViewModel(
            signInUseCase,
            generateEncryptionUseCase,
            userDomainToPresentationMapper
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @Test
    fun `test signIn with invalid email`() {
        // Given
        val email = "johndoe"
        val password = "password"
        val observer = mockk<Observer<Boolean>>(relaxed = true)

        viewModel.signInEnabled.observeForever(observer)

        // When
        viewModel.email.value = email
        viewModel.password.value = password
        viewModel.validateEmail()

        // Then
        verify { observer.onChanged(false) }
    }


    @Test
    fun `test signIn with empty password`() {
        // Given
        val email = "john.doe@example.com"
        val password = ""
        val observer = mockk<Observer<Boolean>>(relaxed = true)

        viewModel.signInEnabled.observeForever(observer)

        // When
        viewModel.email.value = email
        viewModel.password.value = password
        viewModel.validatePassword()

        // Then
        verify { observer.onChanged(false) }
    }

}

       
