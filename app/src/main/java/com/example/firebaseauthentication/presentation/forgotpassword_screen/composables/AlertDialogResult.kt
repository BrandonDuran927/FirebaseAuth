package com.example.firebaseauthentication.presentation.forgotpassword_screen.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebaseauthentication.presentation.forgotpassword_screen.ForgotPasswordViewModel
import com.example.firebaseauthentication.presentation.login_screen.LoginScreenViewModel

@Composable
fun AlertDialogResult(
    viewModel: ForgotPasswordViewModel,
    result: Result<Unit>?,
    isReset: MutableState<Boolean>
) {
    if (result?.isFailure == true) {
        AlertDialog(
            modifier = Modifier.alpha(0.7f),
            onDismissRequest = {
                viewModel.resetResult()
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
                        text = "Email is not recognize",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
    } else if (result?.isSuccess == true) {
        AlertDialog(
            modifier = Modifier.alpha(0.7f),
            onDismissRequest = {
                viewModel.resetResult()
                isReset.value = true
            },
            confirmButton = {
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CheckCircle,
                        modifier = Modifier.size(50.dp),
                        contentDescription = null
                    )
                    Text(
                        text = "Please check your email for the link to reset your password.",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
    }
}