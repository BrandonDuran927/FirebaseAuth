package com.example.firebaseauthentication.presentation.signup_screen

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.firebaseauthentication.presentation.login_screen.composables.AlertDialogCircularProgressIndicator
import com.example.firebaseauthentication.presentation.signup_screen.composables.AlertDialogResult
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    signIn: () -> Unit
) {
    val signUpState by viewModel.signUpState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Create account")
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                modifier = Modifier.width(280.dp),
                value = signUpState.email,
                onValueChange = {
                    viewModel.onEmailChange(it)
                },
                label = {
                    Text("Email address")
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
                value = signUpState.password,
                onValueChange = {
                    viewModel.onPasswordChange(it)
                },
                label = {
                    Text("Password")
                },
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Lock,
                        contentDescription = null
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(16.dp))

            TextField(
                modifier = Modifier.width(280.dp),
                value = signUpState.repeatPassword,
                onValueChange = {
                    viewModel.onRepeatPasswordChange(it)
                },
                label = {
                    Text("Repeat password")
                },
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Lock,
                        contentDescription = null
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .padding(start = 64.dp, end = 64.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(0),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = {
                    viewModel.createAccount()
                }
            ) {
                Text(
                    text = "Create Account",
                    color = Color.White
                )
            }

            Spacer(Modifier.height(16.dp))

            TextButton(
                onClick = signIn
            ) {
                Text(
                    text = "Already have an account? Sign-in",
                    color = MaterialTheme.colorScheme.primary
                )
            }

            AlertDialogResult(
                viewModel = viewModel,
                signUpState = signUpState
            )
        }
    }
}