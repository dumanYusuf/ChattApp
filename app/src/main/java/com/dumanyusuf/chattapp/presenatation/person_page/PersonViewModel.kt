package com.dumanyusuf.chattapp.presenatation.person_page

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PersonViewModel : ViewModel() {

    private val _profileImageUrl = MutableStateFlow<String?>(null)
    val profileImageUrl: StateFlow<String?> = _profileImageUrl


    fun uploadProfileImage(uri: Uri) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val storageRef = FirebaseStorage.getInstance().reference
            .child("usersProfilUrl/$userId.jpg")

        storageRef.putFile(uri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    val url = downloadUri.toString()
                    Log.i("🔥", "URL Alındı: $url")
                    _profileImageUrl.value = url

                    FirebaseFirestore.getInstance().collection("Users")
                        .document(userId)
                        .update("userProfilPage", url)
                        .addOnSuccessListener {
                            Log.i("sucesss", "Firestore userProfilPage güncellendi")
                        }
                        .addOnFailureListener {
                            Log.e("fail", "Firestore güncelleme hatası", it)
                        }
                }
            }
            .addOnFailureListener {
                Log.e("fail", "Fotoğraf yükleme hatası", it)
            }
    }

    fun setProfileImageFromFirestore() {
        if (_profileImageUrl.value !=null){
            Log.e("🔥", "URL zaten mevcut, Firestore'dan çekilmedi: ${_profileImageUrl.value}")
            return
        }
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        FirebaseFirestore.getInstance().collection("Users")
            .document(userId)
            .get()
            .addOnSuccessListener { snapshot ->
                val url = snapshot.getString("userProfilPage")
                _profileImageUrl.value = url
            }
    }
}
