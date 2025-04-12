package com.dumanyusuf.chattapp.data.repo

import com.dumanyusuf.chattapp.domain.model.Users
import com.dumanyusuf.chattapp.domain.repo.AuthRepo
import com.dumanyusuf.chattapp.util.Resource
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val auth:FirebaseAuth,
    private val firebase:FirebaseFirestore
):AuthRepo {

    override suspend fun signUp(userName: String, userMail: String, passWord: String): Resource<Users> {
       return try {
         val authResult=auth.createUserWithEmailAndPassword(userMail,passWord).await()
           val firebaseUser=authResult.user
           if (firebaseUser!=null){
               val newUser=Users(
                   userId = firebaseUser.uid,
                   userName = userName,
                   userMail = userMail,
                   userProfilPage = "https://t4.ftcdn.net/jpg/00/65/77/27/360_F_65772719_A1UV5kLi5nCEWI0BNLLiFaBPEkUbv5Fv.jpg",
                   creatAt = Timestamp.now()
               )
               firebase.collection("Users").document(firebaseUser.uid).set(newUser.toMap()).await()
               Resource.Success(newUser)
           }
           else{
               Resource.Error("user not found")
           }

       }
       catch (e:Exception){
           Resource.Error("error:${e.localizedMessage}")
       }
    }

    override suspend fun sigin(userMail: String, passWord: String): Resource<Users> {
        return  try {
            val authResult=auth.signInWithEmailAndPassword(userMail,passWord).await()
            val firebaseUser=authResult.user

            if (firebaseUser!=null){
                val userDocument=firebase.collection("Users").document(firebaseUser.uid).get().await()
                val user=userDocument.toObject(Users::class.java)
                if (user!=null){
                    Resource.Success(user)
                }
                else{
                    Resource.Error("kullanıcı bıulunamadı")
                }
            }
            else{
                Resource.Error("hata cıktı")
            }

        }
        catch (e:Exception){
            Resource.Error("Hata cıktı:${e.localizedMessage}")
        }
    }

    override fun logOut() {
        try {
            auth.signOut()
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }
}