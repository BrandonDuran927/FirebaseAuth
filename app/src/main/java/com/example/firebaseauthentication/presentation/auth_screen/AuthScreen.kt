package com.example.firebaseauthentication.presentation.auth_screen

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.example.firebaseauthentication.R
import com.example.firebaseauthentication.navigation.HOME_SCREEN
import kotlin.math.sign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    onLogin: () -> Unit,
    onCreateAccount: () -> Unit,
    onSignInGoogle: () -> Unit,
    onTmp: () -> Unit,
    viewModel: AuthScreenViewModel = hiltViewModel(),
    launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>
) {
    val signInResult by viewModel.signInResult.collectAsStateWithLifecycle()


    LaunchedEffect(signInResult.result) {
        Log.d("GoogleSignIn", "LaunchedEffect - result: ${signInResult.result}")
        signInResult.result?.let {
            launcher.launch(
                IntentSenderRequest.Builder(it.pendingIntent.intentSender).build()
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Firebase Authentication")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))

            Button(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .shadow(1.dp, RoundedCornerShape(0))
                    .fillMaxWidth(),
                shape = RoundedCornerShape(0),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                onClick = onLogin
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sign In",
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }

            Button(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .shadow(1.dp, RoundedCornerShape(0))
                    .fillMaxWidth(),
                shape = RoundedCornerShape(0),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                onClick = onCreateAccount
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Create Account",
                        color = Color.Black
                    )
                    Icon(
                        imageVector = Icons.Rounded.Create,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                onClick = onSignInGoogle
            ) {
                Row(
                    modifier = Modifier.width(150.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sign-in with Google",
                        color = Color.Black
                    )
                    Image(
                        modifier = Modifier.size(15.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.google_icon),
                        contentDescription = null
                    )
                }
            }

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                onClick = onTmp
            ) {
                Row(
                    modifier = Modifier.width(150.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "tmp",
                        color = Color.Black
                    )
                }
            }
        }
    }
}

