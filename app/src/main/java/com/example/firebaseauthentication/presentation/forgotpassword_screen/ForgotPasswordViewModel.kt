package com.example.firebaseauthentication.presentation.forgotpassword_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseauthentication.data.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repository: AuthenticationRepository
): ViewModel() {

    private val _resetPasswordState = MutableStateFlow<Result<Unit>?>(null)
    val resetPasswordState = _resetPasswordState.asStateFlow()

    fun resetPassword(email: String) {
        viewModelScope.launch {
            repository.resetPasswordEmail(email)
                .onEach { result ->
                    _resetPasswordState.value = result
                }.catch { e ->
                    _resetPasswordState.value = Result.failure(e)
                }.collect()
        }
    }

    fun resetResult() {
        _resetPasswordState.value = null
    }
}