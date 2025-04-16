package com.dumanyusuf.chattapp.domain.model

import com.google.firebase.Timestamp

data class Users(
    val userId:String="",
    val userName:String="",
    val userMail:String="",
    val userProfilPage:String="",
    val creatAt:Timestamp= Timestamp.now(),
    val fcmToken:String=""
){
    fun toMap():Map<String,Any>{
        return mapOf(
            "userId" to userId,
            "userName" to userName,
            "userMail" to userMail,
            "userProfilPage" to userProfilPage,
            "creatAt" to creatAt,
            "fcmToken" to fcmToken
        )
    }
}
