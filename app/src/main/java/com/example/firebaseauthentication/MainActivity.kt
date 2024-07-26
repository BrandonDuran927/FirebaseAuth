package com.example.firebaseauthentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauthentication.navigation.NavigationGraph
import com.example.firebaseauthentication.presentation.auth_screen.AuthScreen
import com.example.firebaseauthentication.presentation.auth_screen.AuthScreenViewModel
import com.example.firebaseauthentication.presentation.settings.SettingsScreenViewModel
import com.example.firebaseauthentication.ui.theme.FirebaseAuthenticationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirebaseAuthenticationTheme {
                val navController = rememberNavController()


                NavigationGraph(
                    navController = navController
                )


            }
        }
    }
}

