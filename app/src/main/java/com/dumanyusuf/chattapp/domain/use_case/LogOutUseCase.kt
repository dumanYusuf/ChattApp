package com.dumanyusuf.chattapp.domain.use_case

import com.dumanyusuf.chattapp.domain.repo.AuthRepo
import javax.inject.Inject

class LogOutUseCase @Inject constructor(private val repo: AuthRepo) {

     fun LogOut(){
        return repo.logOut()
    }

}