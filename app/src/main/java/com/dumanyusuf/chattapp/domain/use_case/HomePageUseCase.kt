package com.dumanyusuf.chattapp.domain.use_case

import com.dumanyusuf.chattapp.domain.model.Users
import com.dumanyusuf.chattapp.domain.repo.FirebaseRepo
import com.dumanyusuf.chattapp.util.Resource
import javax.inject.Inject

class HomePageUseCase @Inject constructor(private val repo: FirebaseRepo) {


    suspend fun getUsersExcept(curentUserId:String):Resource<List<Users>>{
        return repo.getUsersExcept(curentUserId)
    }

}