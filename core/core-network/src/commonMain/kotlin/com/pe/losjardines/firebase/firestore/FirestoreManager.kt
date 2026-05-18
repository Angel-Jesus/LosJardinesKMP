package com.pe.losjardines.firebase.firestore

import com.pe.losjardines.firebase.firestore.model.RegistrationNetwork
import com.pe.losjardines.firebase.firestore.model.RoomStateNetwork

class FirestoreManager(
    private val firestoreService: FirestoreService
) {

    suspend fun send(collection: String, registrationNetwork: RegistrationNetwork){
        firestoreService.send(collection, registrationNetwork)
    }

    suspend fun delete(collection: String, documentPath: String){
        firestoreService.delete(collection, documentPath)
    }

    suspend fun getRoomState(): List<RoomStateNetwork>{
        return firestoreService.getRoomState()
    }

    suspend fun update(collection: String, documentPath: String, data: Map<String, Any>){
        return firestoreService.update(collection, documentPath, data)
    }
}