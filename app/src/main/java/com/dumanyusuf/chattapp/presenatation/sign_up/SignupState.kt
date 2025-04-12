package com.dumanyusuf.chattapp.presenatation.sign_up

import com.dumanyusuf.chattapp.domain.model.Users

data class SignupState(
    val isSuccess:Boolean=false,
    val isLoading:Boolean=false,
    val error:String="",
   // val user:Users?=null
)
