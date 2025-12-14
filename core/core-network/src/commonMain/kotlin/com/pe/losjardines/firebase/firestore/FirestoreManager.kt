package com.pe.losjardines.firebase.firestore

import com.pe.losjardines.firebase.firestore.model.Registration

class FirestoreManager(
    private val firestoreService: FirestoreService
) {

    suspend fun send(registration: Registration){
        firestoreService.send(registration)
    }

    suspend fun delete(collection: String, documentPath: String){
        firestoreService.delete(collection, documentPath)
    }
}