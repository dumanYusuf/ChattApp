package com.dumanyusuf.chattapp

import android.os.Bundle
import android.util.Log
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
import com.dumanyusuf.chattapp.presenatation.splash_screen.SplashScreen
import com.dumanyusuf.chattapp.util.ui.theme.ChattAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder
import java.net.URLEncoder


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        FirebaseMessaging.getInstance().token.addOnCompleteListener {task->
           if (task.isSuccessful){
               val token=task.result
               Log.e("token","$token")
           }
        }

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
    NavHost(navController = controller, startDestination = Screan.SplashScrean.route) {



        composable(Screan.SplashScrean.route){
            SplashScreen(controller)
        }

        composable(Screan.SignUpPage.route){
            SignupPage(nextToLogin = {controller.navigate(Screan.SignInPage.route)}, navController = controller)
        }
        composable(Screan.SignInPage.route){
            SiginPage(navController = controller)
        }
        composable(route = Screan.HomePage.route+"/{users}",
            arguments = listOf(
                navArgument("users"){type= NavType.StringType}
            )
        ) {
            val jsonUser=it.arguments?.getString("users")
            val decodedJsonUser = URLDecoder.decode(jsonUser, "UTF-8")
            val user=Gson().fromJson(decodedJsonUser,Users::class.java)
            HomeScrean(navController = controller, users = user)
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


