package com.dumanyusuf.chattapp

sealed class  Screan(val route:String){

    object SignInPage:Screan("signin_page")
    object SignUpPage:Screan("signup_page")
    object HomePage:Screan("home_page")
    object PersonPage:Screan("person_page")

}