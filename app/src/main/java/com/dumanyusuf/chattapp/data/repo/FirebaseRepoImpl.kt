package com.dumanyusuf.chattapp.data.repo

import com.dumanyusuf.chattapp.domain.model.Users
import com.dumanyusuf.chattapp.domain.repo.FirebaseRepo
import com.dumanyusuf.chattapp.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepoImpl @Inject constructor(private val firebase:FirebaseFirestore) :FirebaseRepo{


    override suspend fun getUsersExcept(curentUserId: String): Resource<List<Users>> {
        return try {
            val snapshot = firebase.collection("Users").get().await()
            val users = snapshot.documents.mapNotNull { it.toObject(Users::class.java) }
                .filter { it.userId != curentUserId}

            Resource.Success(users)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Bilinmeyen hata")
        }
    }

}