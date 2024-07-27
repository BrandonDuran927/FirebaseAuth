package com.example.firebaseauthentication.presentation.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebaseauthentication.data.AuthenticationRepository
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val repository: AuthenticationRepository,
    private val oneTapClient: SignInClient,
) : ViewModel() {
    private val _settingsState = MutableStateFlow(SettingsState())
    val settingsState = _settingsState.asStateFlow()

    fun onLogout() {
        _settingsState.update {
            it.copy(isLogout = true, isDelete = false)
        }
    }

    fun onDelete() {
        _settingsState.update {
            it.copy(isLogout = false, isDelete = true)
        }
    }

    fun resetState() {
        _settingsState.update {
            SettingsState()
        }
    }

    fun resetLogout() {
        _settingsState.update {
            it.copy(isLogout = false)
        }
    }

    fun resetDelete() {
        _settingsState.update {
            it.copy(isDelete = false)
        }
    }

    fun signOutUser() {
        viewModelScope.launch {
            try {
                Log.d("SignOut", "Attempting to sign out with Google")
                oneTapClient.signOut().await()
                Log.d("SignOut", "Google sign-out successful")

                repository.signOutUser()
                Log.d("SignOut", "Repository sign-out successful")
                _settingsState.update {
                    it.copy(isLogoutSuccess = true)
                }
                Log.d("SignOut", "Success: ${_settingsState.value.isLogoutSuccess}")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("SignOut", "${e.message}")
            }
        }
    }


    fun deleteAccount() {
        viewModelScope.launch {
            try {
                repository.deleteUser()
                _settingsState.update {
                    it.copy(
                        isDeleteSuccess = true
                    )
                }
                Log.d("Delete Account", "Successful")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("Delete Account", "${e.message}")
            }
        }
    }
}