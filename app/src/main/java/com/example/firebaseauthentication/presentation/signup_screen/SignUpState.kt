package com.example.firebaseauthentication.presentation.signup_screen

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false
)