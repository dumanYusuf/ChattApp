package com.dumanyusuf.chattapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dumanyusuf.chattapp.domain.model.Users
import com.dumanyusuf.chattapp.presenatation.chatt_page.ChattPage
import com.dumanyusuf.chattapp.presenatation.home_page.HomeScrean
import com.dumanyusuf.chattapp.presenatation.person_page.PersonPage
import com.dumanyusuf.chattapp.presenatation.sign_up.SiginPage
import com.dumanyusuf.chattapp.presenatation.sign_up.SignupPage
import com.dumanyusuf.chattapp.util.ui.theme.ChattAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChattAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                  Box(
                      modifier = Modifier.padding(innerPadding)
                  ){
                    PageController()
                  }

                }
            }
        }
    }
}


@Composable
fun PageController() {

    val curentUser=FirebaseAuth.getInstance().currentUser


    val controller= rememberNavController()
    NavHost(navController = controller, startDestination = if (curentUser!=null) Screan.HomePage.route else Screan.SignUpPage.route) {

        composable(Screan.SignUpPage.route){
            SignupPage(nextToLogin = {controller.navigate(Screan.SignInPage.route)}, navController = controller)
        }
        composable(Screan.SignInPage.route){
            SiginPage(navController = controller)
        }
        composable(Screan.HomePage.route){
            HomeScrean(navController = controller)
        }
        composable(route = Screan.PersonPage.route) {
            PersonPage(navController = controller)
        }
        composable(Screan.ChattPage.route+"/{users}",
            arguments = listOf(
                navArgument("users"){type= NavType.StringType}
            )
        ){
            val jsonUser = it.arguments?.getString("users")
            val decodedJsonUser = URLDecoder.decode(jsonUser, "UTF-8")
            val user = Gson().fromJson(decodedJsonUser, Users::class.java)
            ChattPage(navController = controller, users = user)
        }


    }

}


