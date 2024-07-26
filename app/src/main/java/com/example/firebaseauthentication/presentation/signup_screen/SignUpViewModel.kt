package com.example.firebaseauthentication.presentation.signup_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseauthentication.data.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel(){
    private val _signUpState = MutableStateFlow(SignUpState())
    val signUpState = _signUpState.asStateFlow()

    fun onEmailChange(email: String) {
        _signUpState.value = _signUpState.value.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        _signUpState.value = _signUpState.value.copy(password = password)
    }

    fun onRepeatPasswordChange(repeatPassword: String) {
        _signUpState.value = _signUpState.value.copy(repeatPassword = repeatPassword)
    }

    fun createAccount() {
        viewModelScope.launch {
            val email = _signUpState.value.email.trim()
            val password = _signUpState.value.password.trim()
            val repeatPassword = _signUpState.value.repeatPassword.trim()


            if (email.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
                _signUpState.value = _signUpState.value.copy(errorMessage = "All fields must not be empty")
                return@launch
            }
            if (password != repeatPassword) {
                _signUpState.value = _signUpState.value.copy(errorMessage = "Password must match")
                return@launch
            }
            if (password.toList().size < 8) {
                _signUpState.value = _signUpState.value.copy(errorMessage = "Password must be at least 8 character")
                return@launch
            }


            _signUpState.value = _signUpState.value.copy(isLoading = true, errorMessage = null)

            try {
                repository.signUpWithEmailPassword(email, password).first()
                _signUpState.value = _signUpState.value.copy(isSuccess = true, isLoading = false)
            } catch (e: Exception) {
                _signUpState.value = _signUpState.value.copy(errorMessage = "${e.message}", isLoading = false)            }
        }
    }

    fun resetState() {
        _signUpState.value = SignUpState()
    }

    fun resetErrorMessage() {
        _signUpState.value = _signUpState.value.copy(errorMessage = null)
    }
}