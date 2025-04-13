package com.dumanyusuf.chattapp.domain.repo

import com.dumanyusuf.chattapp.domain.model.Users
import com.dumanyusuf.chattapp.util.Resource

interface FirebaseRepo {


    suspend fun getUsersExcept(curentUserId:String):Resource<List<Users>>

}