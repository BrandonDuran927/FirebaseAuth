package com.example.firebaseauthentication.presentation.home_screen

import android.util.Log
import android.widget.Space
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.firebaseauthentication.navigation.AUTH_SCREEN
import com.example.firebaseauthentication.navigation.SETTINGS_SCREEN
import com.example.firebaseauthentication.presentation.home_screen.composable.AlertDialogErrorMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val homeScreenState by viewModel.homeScreenState.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        viewModel.retrieveUser()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Text("Home Screen")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            modifier = Modifier.size(25.dp),
                            onClick = {
                                navController.navigate(SETTINGS_SCREEN)
                            }
                        ) {
                            Icon(
                                Icons.Rounded.Settings,
                                contentDescription = null
                            )
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
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
            if (homeScreenState.errorMessage == null) {
                Text(
                    text = "Welcome User",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 32.sp
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Email: ${homeScreenState.email}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "ID: ${homeScreenState.uid}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Email Verified: ${homeScreenState.isEmailVerified}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp
                )

                Spacer(Modifier.height(16.dp))
            } else {
                AlertDialogErrorMessage(
                    errorMessage = homeScreenState.errorMessage,
                    viewModel = viewModel
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun Try() {
    val navController: NavController = rememberNavController()
    HomeScreen(
        navController = navController
    )
}