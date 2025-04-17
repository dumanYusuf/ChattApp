package com.dumanyusuf.chattapp.presenatation.sign_up

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dumanyusuf.chattapp.domain.use_case.SignUpUseCase
import com.dumanyusuf.chattapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SignupViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase) : ViewModel() {

    private val _signupState = MutableStateFlow<SignupState>(SignupState())
    val signupState: StateFlow<SignupState> = _signupState.asStateFlow()

    val _emailError = MutableStateFlow("")
    val emailError: StateFlow<String> = _emailError.asStateFlow()

    val _usernameError = MutableStateFlow("")
    val usernameError: StateFlow<String> = _usernameError.asStateFlow()

    val _passwordError = MutableStateFlow("")
    val passwordError: StateFlow<String> = _passwordError.asStateFlow()

    val _confirmPasswordError = MutableStateFlow("")
    val confirmPasswordError: StateFlow<String> = _confirmPasswordError.asStateFlow()

    fun validateInput(field: String, setError: (String) -> Unit, errorMessage: String): Boolean {
        return if (field.isBlank()) {
            setError(errorMessage)
            false
        } else {
            setError("")
            true
        }
    }

    // E-posta doğrulama
    fun validateEmail(email: String): Boolean {
        return when {
            email.isBlank() -> {
                _emailError.value = "Email alanı boş geçilemez"
                false
            }
            !email.endsWith("@gmail.com") -> {
                _emailError.value = "Sadece @gmail.com uzantılı e-postalar kabul edilir"
                false
            }
            else -> {
                _emailError.value = ""
                true
            }
        }
    }

    // Şifre doğrulama
    fun validatePassword(password: String): Boolean {
        return when {
            password.isBlank() -> {
                _passwordError.value = "Şifre alanı boş geçilemez"
                false
            }
            password.length < 6 -> {
                _passwordError.value = "Şifre en az 6 karakter olmalıdır"
                false
            }
            else -> {
                _passwordError.value = ""
                true
            }
        }
    }

    // Şifre onayı doğrulama
    fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
        return when {
            confirmPassword.isBlank() -> {
                _confirmPasswordError.value = "Şifreyi tekrar giriniz"
                false
            }
            password != confirmPassword -> {
                _confirmPasswordError.value = "Şifreler eşleşmiyor"
                false
            }
            else -> {
                _confirmPasswordError.value = ""
                true
            }
        }
    }

    // Tüm alanları doğrulama
    fun validateInputs(userMail: String, userName: String, password: String, confirmPassword: String): Boolean {
        val isUsernameValid = validateInput(userName, { _usernameError.value = it }, "Kullanıcı adı boş geçilemez")
        val isEmailValid = validateEmail(userMail)
        val isPasswordValid = validatePassword(password)
        val isConfirmPasswordValid = validateConfirmPassword(password, confirmPassword)

        return isEmailValid && isUsernameValid && isPasswordValid && isConfirmPasswordValid
    }

    // Kullanıcı kaydı
    fun registerUser(userName: String, userMail: String, password: String) {
        viewModelScope.launch {
            _signupState.value = SignupState(isLoading = true)
            val result = signUpUseCase.signUpUser(userName, userMail, password)
            when (result) {
                is Resource.Success -> {
                    _signupState.value = SignupState(
                        isSuccess = true,
                    )
                    Log.e("user","${result.data}")
                }
                is Resource.Loading -> {
                    _signupState.value = SignupState(isLoading = true)
                }
                is Resource.Error -> {
                    _signupState.value = SignupState(error = "user error:${result.message}")
                }
            }
        }
    }
}