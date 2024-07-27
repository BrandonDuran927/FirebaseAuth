package com.example.firebaseauthentication.presentation.login_screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.firebaseauthentication.navigation.AUTH_SCREEN
import com.example.firebaseauthentication.navigation.HOME_SCREEN
import com.example.firebaseauthentication.presentation.login_screen.composables.AlertDialogCircularProgressIndicator
import com.example.firebaseauthentication.presentation.login_screen.composables.AlertDialogErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlin.math.log
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = hiltViewModel(),
    createAccount: () -> Unit,
    forgotPassword: () -> Unit
) {
    val loginScreenState by viewModel.loginState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Enter your login details")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier.width(280.dp),
                value = loginScreenState.email.toString(),
                onValueChange = {
                    viewModel.onEmailChange(it)
                },
                label = {
                    Text("Email..")
                },
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Email,
                        contentDescription = null
                    )
                },
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            TextField(
                modifier = Modifier.width(280.dp),
                value = loginScreenState.password.toString(),
                onValueChange = {
                    viewModel.onPasswordChange(it)
                },
                label = {
                    Text("Password..")
                },
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Lock,
                        contentDescription = null
                    )
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )

            Spacer(Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .padding(start = 64.dp, end = 64.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(0),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = {
                    viewModel.loginUser()
                }
            ) {
                Text(
                    text = "Sign In",
                    color = Color.White
                )
            }

            Spacer(Modifier.height(16.dp))

            TextButton(
                onClick = forgotPassword
            ) {
                Text(
                    text = "Forgot password? Click to get recovery",
                    color = MaterialTheme.colorScheme.primary
                )
            }
            TextButton(
                onClick = createAccount
            ) {
                Text(
                    text = "No account yet? Click here to create",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            AlertDialogCircularProgressIndicator(
                loginScreenState.isLoading
            )

            AlertDialogErrorMessage(
                viewModel = viewModel,
                errorMessage = loginScreenState.errorMessage
            )

            if (loginScreenState.isSuccess) {
                navController.navigate(HOME_SCREEN) {
                    popUpTo(0) {
                        inclusive = true
                    }
                }
                viewModel.resetLoginState()
            }

        }

        BackHandler {
            navController.popBackStack(AUTH_SCREEN, false)
        }
    }
}

