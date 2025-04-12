package com.dumanyusuf.chattapp.data.di

import com.dumanyusuf.chattapp.data.repo.AuthRepoImpl
import com.dumanyusuf.chattapp.domain.repo.AuthRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    @Singleton
    fun provideAuth():FirebaseAuth=FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebase():FirebaseFirestore=FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun repoProvide(auth: FirebaseAuth,firestore: FirebaseFirestore): AuthRepo {
        return AuthRepoImpl(auth, firestore)
    }


    }