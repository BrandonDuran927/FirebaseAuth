package com.example.firebaseauthentication.presentation.login_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseauthentication.data.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginScreenState())
    val loginState = _loginState.asStateFlow()


    fun onEmailChange(email: String?) {
        _loginState.value = loginState.value.copy(email = email)
    }

    fun onPasswordChange(password: String?) {
        _loginState.value = loginState.value.copy(password = password)
    }

    fun loginUser() {
        viewModelScope.launch {
            val email = loginState.value.email?.trim()
            val password = loginState.value.password?.trim()

            if (email.isNullOrBlank() || password.isNullOrBlank()) {
                _loginState.value = loginState.value.copy(errorMessage = "Email or password must not be empty")
                Log.d("ButtonPress", "1. ${loginState.value.isLoading} ${loginState.value.errorMessage}")
                return@launch
            }

            _loginState.value = loginState.value.copy(isLoading = true, errorMessage = null)
            Log.d("ButtonPress", "1.5 ${loginState.value.isLoading}")


            try {
                repository.signInWithEmailPassword(email, password).first()
                _loginState.value = loginState.value.copy(isLoading = false, isSuccess = true)
                Log.d("ButtonPress", "2. ${loginState.value.errorMessage}")
            } catch (e: Exception) {
                _loginState.value = loginState.value.copy(isLoading = false, errorMessage = e.message)
                Log.d("ButtonPress", "3. ${loginState.value.isLoading} ${loginState.value.errorMessage}")
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = LoginScreenState()
    }

    fun resetErrorMessage() {
        _loginState.value = _loginState.value.copy(errorMessage = null)
    }
}