package com.pe.losjardines.di

import com.pe.losjardines.firebase.auth.LoginManager
import com.pe.losjardines.firebase.auth.LoginService
import com.pe.losjardines.firebase.firestore.FirestoreManager
import com.pe.losjardines.firebase.firestore.FirestoreService
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val firebaseModules = module {
    single<FirebaseAuth>{ Firebase.auth }
    single<FirebaseFirestore>{ Firebase.firestore }
    singleOf(::LoginService)
    singleOf(::LoginManager)
    singleOf(::FirestoreService)
    singleOf(::FirestoreManager)
}