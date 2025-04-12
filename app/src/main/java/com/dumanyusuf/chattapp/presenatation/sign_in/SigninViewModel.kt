package com.dumanyusuf.chattapp.presenatation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumanyusuf.chattapp.domain.use_case.SigninUseCase
import com.dumanyusuf.chattapp.presenatation.sign_up.SignupState
import com.dumanyusuf.chattapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SigninViewModel @Inject constructor(
    private val signinUseCase: SigninUseCase
):ViewModel() {


    private val _signinState=MutableStateFlow<SigninState>(SigninState())
    val stateLogin:StateFlow<SigninState> = _signinState.asStateFlow()

    fun loginUser(userMail: String, password: String) {
        viewModelScope.launch {
            _signinState.value = SigninState(isLoading = true)
            val result = signinUseCase.signin(userMail,password)
            when (result) {
                is Resource.Success -> {
                    _signinState.value = SigninState(isSuccess = true,)
                }
                is Resource.Loading -> {
                    _signinState.value = SigninState(isLoading = true)
                }
                is Resource.Error -> {
                    _signinState.value = SigninState(error = "user error:${result.message}")
                }
            }
        }
    }


}