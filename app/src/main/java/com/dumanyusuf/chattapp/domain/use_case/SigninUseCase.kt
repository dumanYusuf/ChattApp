package com.dumanyusuf.chattapp.domain.use_case

import com.dumanyusuf.chattapp.domain.model.Users
import com.dumanyusuf.chattapp.domain.repo.AuthRepo
import com.dumanyusuf.chattapp.util.Resource
import javax.inject.Inject

class SigninUseCase @Inject constructor(private val repo: AuthRepo) {


    suspend fun signin(userMail:String,password:String):Resource<Users>{
        return repo.sigin(userMail,password)
    }

}