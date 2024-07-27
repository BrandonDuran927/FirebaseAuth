package com.example.firebaseauthentication.presentation.auth_screen

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebaseauthentication.R
import com.example.firebaseauthentication.data.AuthenticationRepository
import com.example.firebaseauthentication.presentation.login_screen.LoginScreenState
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    private val repository: AuthenticationRepository,
    private val oneTapClient: SignInClient,
    private val signInRequest: BeginSignInRequest
) : ViewModel() {
    private val _signInResult = MutableStateFlow(GoogleSignInState())
    val signInResult = _signInResult.asStateFlow()


    suspend fun startSignIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(signInRequest).await()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        return result?.pendingIntent?.intentSender
    }

    suspend fun handleSignInResult(intent: Intent?): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleId = credential.googleIdToken
        val googleCredential = GoogleAuthProvider.getCredential(googleId, null)

        return try {
            val user = repository.signInWithGoogle(googleCredential).first().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = user.uid,
                        userEmail = user.email,
                        isEmailVerified = false
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {  // Return null to data
            e.printStackTrace()
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    fun onSignInResult(result: SignInResult) {
        _signInResult.update {
            it.copy(
                isSuccess = result.data != null,
                errorMessageSignIn = result.errorMessage
            )
        }
    }

    fun resetState() {
        _signInResult.update {
            GoogleSignInState()
        }
    }
}