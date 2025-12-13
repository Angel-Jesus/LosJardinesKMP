package com.pe.losjardines

import android.app.Application
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.google.firebase.FirebasePlatform
import com.pe.losjardines.BuildKonfig.API_KEY
import com.pe.losjardines.BuildKonfig.APPLICATION_ID
import com.pe.losjardines.BuildKonfig.PROJECT_ID
import dev.gitlive.firebase.FirebaseOptions
import com.pe.losjardines.di.initKoinModularization
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize


fun main() = application {
    FirebasePlatform.initializeFirebasePlatform(object: FirebasePlatform(){
        val storage = mutableMapOf<String, String>()

        override fun clear(key: String) {
            storage.remove(key)
        }

        override fun log(msg: String) {
            println("message: $msg")
        }

        override fun retrieve(key: String): String? = storage[key]

        override fun store(key: String, value: String) = storage.set(key, value)

    })

    val options = FirebaseOptions(
        projectId = PROJECT_ID,
        applicationId = APPLICATION_ID,
        apiKey = API_KEY
    )

    Firebase.initialize(Application(), options)

    initKoinModularization()

    Window(
        onCloseRequest = ::exitApplication,
        alwaysOnTop = true,
        title = "LosJardinesKMP",
    ) {
        App()
    }
}