package com.example.firebaseauthentication.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.firebaseauthentication.navigation.AUTH_SCREEN
import com.example.firebaseauthentication.navigation.HOME_SCREEN
import com.example.firebaseauthentication.presentation.settings.composable.AlertDialogLogout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsScreenViewModel = hiltViewModel(),
    navController: NavHostController
) {


  Scaffold(
      topBar = {
          TopAppBar(
              title = {
                  Text("Settings")
              },
              colors = topAppBarColors(
                  containerColor = MaterialTheme.colorScheme.primaryContainer,
                  titleContentColor = MaterialTheme.colorScheme.primary
              ),
              navigationIcon = {
                  IconButton(onClick = {
                      navController.navigate(HOME_SCREEN)
                  }) {
                      Icon(
                          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                          contentDescription = "Localized description",
                          tint = MaterialTheme.colorScheme.surfaceTint
                      )
                  }
              }
          )
      }
  ) { innerPadding ->
      Column(
          modifier = Modifier
              .fillMaxSize()
              .padding(innerPadding),
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
          Spacer(Modifier.height(8.dp))

          Button(
              modifier = Modifier
                  .padding(start = 16.dp, end = 16.dp)
                  .shadow(1.dp, RoundedCornerShape(0))
                  .fillMaxWidth(),
              shape = RoundedCornerShape(0),
              colors = ButtonDefaults.buttonColors(containerColor = Color.White),
              onClick = {
                  viewModel.onLogout()
              }
          ) {
              Row(
                  modifier = Modifier
                      .fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
              ) {
                  Text(
                      text = "Logout",
                      color = Color.Black
                  )
                  Icon(
                      imageVector = Icons.AutoMirrored.Rounded.ExitToApp,
                      contentDescription = null,
                      tint = Color.Black
                  )
              }
          }

          Button(
              modifier = Modifier
                  .padding(start = 16.dp, end = 16.dp)
                  .shadow(1.dp, RoundedCornerShape(0), spotColor = Color.Red)
                  .fillMaxWidth(),
              shape = RoundedCornerShape(0),
              colors = ButtonDefaults.buttonColors(containerColor = Color.White),
              onClick = {
                  viewModel.onDelete()
              }
          ) {
              Row(
                  modifier = Modifier
                      .fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
              ) {
                  Text(
                      text = "Delete Account",
                      color = Color.Red
                  )
                  Icon(
                      imageVector = Icons.Rounded.Delete,
                      contentDescription = null,
                      tint = Color.Red
                  )
              }
          }

          AlertDialogLogout(
              navController = navController
          )
      }
  }
}

