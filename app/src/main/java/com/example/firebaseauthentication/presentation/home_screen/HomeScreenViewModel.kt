package com.example.firebaseauthentication.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseauthentication.data.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: AuthenticationRepository
) : ViewModel() {
    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()

    fun retrieveUser() {
        viewModelScope.launch {
            val user = repository.retrieveUser().first()
            try {
                if (user != null) {
                    _homeScreenState.value = _homeScreenState.value.copy(
                        email = user.email,
                        uid = user.uid,
                        isEmailVerified = user.isEmailVerified
                    )
                }
            } catch (e: Exception) {
                _homeScreenState.value = _homeScreenState.value.copy(
                    errorMessage = e.message
                )
            }
        }
    }

    fun resetState() {
        _homeScreenState.value = HomeScreenState()
    }
}