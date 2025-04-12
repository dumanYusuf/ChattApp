package com.dumanyusuf.chattapp.presenatation.sign_in

data class SigninState(
    val isSuccess:Boolean=false,
    val isLoading:Boolean=false,
    val error:String=""
)