package com.dumanyusuf.chattapp.presenatation.splash_screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.dumanyusuf.chattapp.Screan
import com.dumanyusuf.chattapp.domain.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.delay
import java.net.URLEncoder

@Composable
fun SplashScreen(navController: NavController) {

    val auth = FirebaseAuth.getInstance()
    val firestore = Firebase.firestore
    val currentUser = auth.currentUser

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }

    LaunchedEffect(Unit) {
        Log.e("splash","splashScrene girdi")
        delay(1500)

        if (currentUser != null) {
            val userId = currentUser.uid

            firestore.collection("Users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    Log.e("spalsh","basarılı splash screene")
                    val user = document.toObject(Users::class.java)
                    if (user != null) {

                        val json = Gson().toJson(user)
                        val encoded = URLEncoder.encode(json, "UTF-8")

                        navController.navigate(Screan.HomePage.route + "/$encoded") {
                            popUpTo(Screan.SplashScrean.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Screan.SignUpPage.route) {
                            popUpTo(Screan.SplashScrean.route) { inclusive = true }
                        }
                    }
                }
        } else {
            navController.navigate(Screan.SignUpPage.route) {
                popUpTo(Screan.SplashScrean.route) { inclusive = true }
            }
        }
    }
}
