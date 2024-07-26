package com.example.firebaseauthentication.navigation

import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.autoSaver
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauthentication.presentation.auth_screen.AuthScreen
import com.example.firebaseauthentication.presentation.auth_screen.AuthScreenViewModel
import com.example.firebaseauthentication.presentation.forgotpassword_screen.ForgotPasswordScreen
import com.example.firebaseauthentication.presentation.home_screen.HomeScreen
import com.example.firebaseauthentication.presentation.home_screen.HomeScreenViewModel
import com.example.firebaseauthentication.presentation.login_screen.LoginScreen
import com.example.firebaseauthentication.presentation.settings.SettingsScreen
import com.example.firebaseauthentication.presentation.settings.SettingsScreenViewModel
import com.example.firebaseauthentication.presentation.signup_screen.SignUpViewModel
import com.example.firebaseauthentication.presentation.signup_screen.SignupScreen
import kotlinx.coroutines.launch
import kotlin.math.sign

@Composable
fun NavigationGraph(
    viewModel: AuthScreenViewModel = hiltViewModel(),
    viewModel1: HomeScreenViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    //launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
) {
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = AUTH_SCREEN
    ) {
        composable(route = AUTH_SCREEN) {
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        scope.launch {
                            val signInResult = viewModel.handleSignInResult(
                                intent = result.data ?: return@launch
                            )
                            viewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            val signInResult by viewModel.signInResult.collectAsStateWithLifecycle()

            if (signInResult.isSuccess) {
                navController.navigate(HOME_SCREEN)
                viewModel.resetState()
            }

            AuthScreen(
                onLogin = {
                    navController.navigate(LOGIN_SCREEN)
                },
                onCreateAccount = {
                    navController.navigate(SIGNUP_SCREEN)
                },
                onTmp = {
                    navController.navigate(HOME_SCREEN)
                },
                launcher = launcher,
                onSignInGoogle = {
                    // TODO: Maybe this is what it cause the problem..
                    scope.launch {
                        val signInIntentSender = viewModel.startSignIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                intentSender = signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
//                    navController.navigate(HOME_SCREEN)
                }
            )
        }

        composable(route = LOGIN_SCREEN) {
            LoginScreen(
                navController = navController,
                createAccount = {
                    navController.navigate(SIGNUP_SCREEN)
                },
                forgotPassword = {
                    navController.navigate(FORGOTPASSWORD_SCREEN)
                }
            )
        }

        composable(route = SIGNUP_SCREEN) {
            SignupScreen(
                signIn = {
                    navController.navigate(LOGIN_SCREEN)
                }
            )
        }

        composable(route = HOME_SCREEN) {
            val homeScreenState by viewModel1.homeScreenState.collectAsStateWithLifecycle()

//            if (homeScreenState.email == null) {
//                Log.d("Navigation", "1. Navigating to auth screen")
//                navController.navigate(AUTH_SCREEN)
//            }

            HomeScreen(
                navController = navController
            )
        }

        composable(route = FORGOTPASSWORD_SCREEN) {
            ForgotPasswordScreen(
                onSignIn = {
                    navController.navigate(LOGIN_SCREEN)
                }
            )
        }

        composable(route = SETTINGS_SCREEN) {
            SettingsScreen(navController = navController)
        }
    }
}