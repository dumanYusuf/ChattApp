package com.dumanyusuf.chattapp.presenatation.home_page

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dumanyusuf.chattapp.R
import com.dumanyusuf.chattapp.Screan
import com.dumanyusuf.chattapp.domain.model.Users
import com.dumanyusuf.chattapp.presenatation.sign_up.SignupViewModel
import com.google.gson.Gson

@Composable
fun HomeScrean(
    viewModel: HomeViewModel= hiltViewModel(),
    navController: NavController,
) {

    Column (modifier = Modifier.fillMaxSize()){

        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp)
            , verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

            Text(text = stringResource(R.string.chattApp))
            IconButton(
                onClick = {
                    // cıkıs yap
                    viewModel.logOut()
                    navController.navigate(Screan.SignInPage.route)
                }
            ) {
                Icon(painter = painterResource(R.drawable.logout), contentDescription = "")
            }
            IconButton(
                onClick = {
                   // go to profil

                    navController.navigate(Screan.PersonPage.route)

                }
            ) {
                Icon(painter = painterResource(R.drawable.person), contentDescription = "")
            }
            IconButton(
                onClick = {
                  // search kısmı
                }
            ) {
                Icon(painter = painterResource(R.drawable.search), contentDescription = "")
            }
        }


    }

}