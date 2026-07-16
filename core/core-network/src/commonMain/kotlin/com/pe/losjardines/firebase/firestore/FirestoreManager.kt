package com.pe.losjardines.firebase.firestore

import com.pe.losjardines.firebase.firestore.model.RegistrationNetwork
import com.pe.losjardines.firebase.firestore.model.RoomStateNetwork
import com.pe.losjardines.firebase.firestore.model.TrashNetwork

class FirestoreManager(
    private val firestoreService: FirestoreService
) {

    suspend fun send(collection: String, registrationNetwork: RegistrationNetwork){
        firestoreService.send(collection, registrationNetwork)
    }

    suspend fun getRegistrations(collection: String): List<RegistrationNetwork>{
        return firestoreService.getRegistrations(collection)
    }

    suspend fun sendTrash(trashNetwork: TrashNetwork){
        firestoreService.sendTrash(trashNetwork)
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