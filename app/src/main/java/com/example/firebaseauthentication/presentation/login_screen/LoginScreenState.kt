package com.example.firebaseauthentication.presentation.login_screen

data class LoginScreenState(
    val email: String? = "",
    val password: String? = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)