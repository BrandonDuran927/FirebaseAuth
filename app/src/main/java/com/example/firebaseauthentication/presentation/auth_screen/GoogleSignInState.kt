package com.example.firebaseauthentication.presentation.auth_screen

import com.google.android.gms.auth.api.identity.BeginSignInResult

data class GoogleSignInState(
    val result: BeginSignInResult? = null,
    val isSuccess: Boolean = false,
    val isSuccessSignIn: Boolean = false,
    val isSuccessSignInWithIntent: Boolean = false,
    val errorMessageSignIn: String? = null,
    val errorMessageSignInWithIntent: String? = null,
)

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

data class UserData(
    val userId: String,
    val userEmail: String?,
    val isEmailVerified: Boolean
)

