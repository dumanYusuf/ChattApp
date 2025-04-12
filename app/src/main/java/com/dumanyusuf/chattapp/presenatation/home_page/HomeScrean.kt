package com.dumanyusuf.chattapp.presenatation.home_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dumanyusuf.chattapp.R
import com.dumanyusuf.chattapp.Screan
import com.dumanyusuf.chattapp.domain.model.Users
import com.dumanyusuf.chattapp.presenatation.sign_up.SignupViewModel

@Composable
fun HomeScrean(
    viewModel: HomeViewModel= hiltViewModel(),
    navController: NavController
) {



    Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Hosgeldiniz:")
        IconButton(
            onClick = {
                // cıkıs yap
                viewModel.logOut()
                navController.navigate(Screan.SignInPage.route)
            }
        ) {
            Icon(painter = painterResource(R.drawable.logout), contentDescription = "")
        }
    }

}