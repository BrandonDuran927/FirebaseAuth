package com.example.firebaseauthentication.presentation.login_screen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.firebaseauthentication.presentation.login_screen.LoginScreenViewModel

@Composable
fun AlertDialogErrorMessage(
    viewModel: LoginScreenViewModel,
    errorMessage: String?
) {
    if (errorMessage != null) {
        AlertDialog(
            modifier = Modifier.alpha(0.7f),
            onDismissRequest = {
                viewModel.resetErrorMessage()
            },
            confirmButton = {
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Warning,
                        modifier = Modifier.size(50.dp),
                        contentDescription = null
                    )
                    Text(
                        text = "$errorMessage",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
    }
}