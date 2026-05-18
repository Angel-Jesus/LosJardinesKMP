package com.pe.losjardines.firebase.firestore

import com.pe.losjardines.firebase.firestore.model.RegistrationNetwork
import com.pe.losjardines.firebase.firestore.model.RoomStateNetwork
import com.pe.losjardines.utils.FirestoreConstance
import com.pe.losjardines.utils.getDateNow
import dev.gitlive.firebase.firestore.FirebaseFirestore

class FirestoreService(private val firestore: FirebaseFirestore) {

    suspend fun clearCache(){
        firestore.clearPersistence()
    }

    suspend fun send(collection: String, registrationNetwork: RegistrationNetwork){
        firestore.collection(collection).document(registrationNetwork.id).set(data = registrationNetwork, merge = true)
    }

    suspend fun update(collection: String, documentPath: String, data: Map<String, Any>){
        firestore.collection(collection).document(documentPath).update(data)
    }

    suspend fun delete(collection: String, documentPath: String){
        firestore.collection(collection).document(documentPath).delete()
    }

    suspend fun getRoomState(): List<RoomStateNetwork>{
        return firestore.collection(FirestoreConstance.ROOMS_COLLECTION).get().documents.map { it.data<RoomStateNetwork>().copy(room = it.id) }
    }
}