package com.pe.losjardines.data.remote

import dev.gitlive.firebase.firestore.CollectionReference
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.Query

class FirestoreService(val firestore: FirebaseFirestore) {
    suspend inline fun <reified T: Any> saveData(collection: String, documentPath: String, dataModel: T){
        return firestore.collection(collection)
            .document(documentPath)
            .set(data = dataModel)
    }

    suspend inline fun <reified T> getData(
        collection: String,
        buildQuery: (CollectionReference) -> Query = { it }
    ): List<T>{
        val query = buildQuery(firestore.collection(collection))
        val snapshot = query.get()
        return snapshot.documents.map { it.data<T>() }
    }

    suspend fun <T> updateValueData(collection: String, documentPath: String, values: Map<String, T>){
        return firestore.collection(collection)
            .document(documentPath)
            .update(values)
    }

    suspend inline fun <reified T: Any> updateData(collection: String, documentPath: String, dataModel: T){
        return firestore.collection(collection)
            .document(documentPath)
            .set(data = dataModel, merge = true)
    }

    suspend fun deleteData(collection: String, documentPath: String){
        return firestore.collection(collection).document(documentPath).delete()
    }
}