package com.dumanyusuf.chattapp.presenatation.home_page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dumanyusuf.chattapp.R
import com.dumanyusuf.chattapp.Screan
import com.dumanyusuf.chattapp.domain.model.Users


@Composable
fun HomeScrean(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.stateUsers.value

    LaunchedEffect(Unit) {
        val currentUserId = viewModel.getCurrentUserId()
        viewModel.getUserExcept(currentUserId)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.chattApp))

            IconButton(onClick = {
                viewModel.logOut()
                navController.navigate(Screan.SignInPage.route)
            }) {
                Icon(painter = painterResource(R.drawable.logout), contentDescription = "Çıkış")
            }

            IconButton(onClick = {
                navController.navigate(Screan.PersonPage.route)
            }) {
                Icon(painter = painterResource(R.drawable.person), contentDescription = "Profil")
            }

            IconButton(onClick = {
                // Arama gelecekte eklenecek
            }) {
                Icon(painter = painterResource(R.drawable.search), contentDescription = "Ara")
            }
        }

        if (state.isLoading) {
          Box(
              modifier = Modifier.fillMaxSize(),
              contentAlignment = Alignment.Center
          ){
              CircularProgressIndicator(
                  color = Color.Red,
                  )
          }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.userList) { user ->
                    UserItem(user = user) {

                    }
                }
            }
        }
    }
}


@Composable
fun UserItem(user: Users, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.person),
            contentDescription = "Profil",
            modifier = Modifier.padding(end = 8.dp)
        )
        Column {
            Text(text = user.userName ?: "Bilinmeyen", style = MaterialTheme.typography.titleMedium)
            Text(text = user.userMail ?: "", style = MaterialTheme.typography.bodySmall)
        }
    }
}

