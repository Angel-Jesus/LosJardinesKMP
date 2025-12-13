package com.pe.losjardines.firebase.auth

import dev.gitlive.firebase.auth.AuthResult
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser

class LoginService(val firebaseAuth: FirebaseAuth) {

    suspend fun login(email: String, password: String): AuthResult{
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    suspend fun logout() = firebaseAuth.signOut()

    fun currentUser(): FirebaseUser? = firebaseAuth.currentUser

}