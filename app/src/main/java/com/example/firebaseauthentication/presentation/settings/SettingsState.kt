package com.example.firebaseauthentication.presentation.settings

import androidx.appcompat.app.ActionBarDrawerToggle.Delegate

data class SettingsState(
    val isLogout: Boolean = false,
    val isDelete: Boolean = false,
    val isLogoutSuccess: Boolean = false,
    val isDeleteSuccess: Boolean = false
)
