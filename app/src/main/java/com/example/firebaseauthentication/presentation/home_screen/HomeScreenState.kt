package com.example.firebaseauthentication.presentation.home_screen

data class HomeScreenState(
    val email: String? = null,
    val uid: String = "",
    val isEmailVerified: Boolean = false,
    val errorMessage: String? = null
)
