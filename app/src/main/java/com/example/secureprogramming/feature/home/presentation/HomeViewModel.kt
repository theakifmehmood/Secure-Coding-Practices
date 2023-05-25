package com.example.secureprogramming.feature.home.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.secureprogramming.feature.authentication.domain.usercases.LogoutUseCase
import com.example.secureprogramming.feature.crud_password.presentation.model.PasswordPresentationModel
import com.example.secureprogramming.feature.crud_password.domain.usercases.RetrievePasswordUseCase
import com.example.secureprogramming.feature.home.presentation.mapper.PasswordDomainToPresentationListMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val retrievePasswordUseCase: RetrievePasswordUseCase,
    private val passwordDomainToPresentationListMapper: PasswordDomainToPresentationListMapper,
    private val logoutUseCase: LogoutUseCase
): ViewModel() {

    var password = MutableLiveData<List<PasswordPresentationModel>>()

    fun retrievePassword() {
            viewModelScope.launch {
               val result =  retrievePasswordUseCase.retrievePassword()
                if(result.isSuccess){
                    val passwordDomainList = result.getOrNull()
                    val passwordPresentationList = passwordDomainToPresentationListMapper.map(passwordDomainList!!)
                    password.postValue(passwordPresentationList)
                }else {

                }
            }
    }


    fun logout() {
        viewModelScope.launch {
            logoutUseCase.logout()
        }
    }
}
