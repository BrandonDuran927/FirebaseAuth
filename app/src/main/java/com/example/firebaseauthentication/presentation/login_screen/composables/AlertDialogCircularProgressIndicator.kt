package com.example.firebaseauthentication.presentation.login_screen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.firebaseauthentication.presentation.login_screen.LoginScreenViewModel

@Composable
fun AlertDialogCircularProgressIndicator(
    isLoading: Boolean
) {
    if (isLoading) {
        AlertDialog(
            modifier = Modifier.alpha(0.7f),
            onDismissRequest = {
                /* Does not do anything */
            },
            confirmButton = {
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(Modifier.height(100.dp))
                    CircularProgressIndicator()
                    Spacer(Modifier.height(100.dp))
                }

            }
        )
    }
}