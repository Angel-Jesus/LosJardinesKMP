package com.pe.losjardines.firebase.firestore

import com.pe.losjardines.firebase.firestore.model.Registration
import com.pe.losjardines.utils.getDateNow
import dev.gitlive.firebase.firestore.FirebaseFirestore

class FirestoreService(private val firestore: FirebaseFirestore) {

    suspend fun clearCache(){
        firestore.clearPersistence()
    }

    suspend fun send(registration: Registration){
        val collection = getDateNow().year.toString()
        firestore.collection(collection).document(registration.id).set(data = registration, merge = true)
    }

    suspend fun delete(collection: String, documentPath: String){
        firestore.collection(collection).document(documentPath).delete()
    }
}