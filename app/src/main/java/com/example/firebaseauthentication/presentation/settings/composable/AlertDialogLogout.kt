package com.example.firebaseauthentication.presentation.settings.composable

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.firebaseauthentication.navigation.AUTH_SCREEN
import com.example.firebaseauthentication.navigation.HOME_SCREEN
import com.example.firebaseauthentication.presentation.settings.SettingsScreenViewModel

@Composable
fun AlertDialogLogout(
    viewModel: SettingsScreenViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val settingsState by viewModel.settingsState.collectAsStateWithLifecycle()

    LaunchedEffect(settingsState.isLogoutSuccess) {
        if (settingsState.isLogoutSuccess) {
            navController.navigate(AUTH_SCREEN) {
                popUpTo(0) { inclusive = true } // Clear back stack
            }
            viewModel.resetState()
        }
    }

    if (settingsState.isLogout) {
        AlertDialog(
            containerColor = Color.White.copy(alpha = 0.7f),
            onDismissRequest = {
                /* DOES NOT DO ANYTHING */
            },
            confirmButton = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Are you sure you want to logout?",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        lineHeight = 24.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                viewModel.resetLogout()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("No")
                        }

                        Spacer(Modifier.width(8.dp))

                        Button(
                            onClick = {
                                viewModel.signOutUser()
                            }
                        ) {
                            Text("Yes")
                        }
                    }
                }
            }
        )
    }
}

