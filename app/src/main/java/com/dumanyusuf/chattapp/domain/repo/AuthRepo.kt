package com.dumanyusuf.chattapp.domain.repo

import com.dumanyusuf.chattapp.domain.model.Users
import com.dumanyusuf.chattapp.util.Resource

interface AuthRepo {


    suspend fun signUp(userName:String,userMail:String,passWord:String):Resource<Users>
    suspend fun sigin(userMail: String,passWord: String):Resource<Users>
    fun logOut()

}