package com.pe.losjardines.di

import com.pe.losjardines.core.network.HttpClientFactory
import com.pe.losjardines.core.utils.ConnectionUtils
import com.pe.losjardines.core.utils.ConnectionUtilsImpl
import com.pe.losjardines.data.remote.ApiService
import com.pe.losjardines.data.remote.FirestoreService
import com.pe.losjardines.domain.repository.ClientNetworkRepository
import com.pe.losjardines.data.repository.ClientNetworkRepositoryImpl
import com.pe.losjardines.domain.usecase.DeleteClientUseCase
import com.pe.losjardines.domain.usecase.GetClientsUseCase
import com.pe.losjardines.domain.usecase.SendClientUseCase
import com.pe.losjardines.domain.usecase.UpdateClientUseCase
import com.pe.losjardines.presentation.viewmodel.ConsultationViewModel
import com.pe.losjardines.presentation.viewmodel.RegistrationViewModel
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module


val newtworkSharedModule: Module = module {
    single { HttpClientFactory().createClient() }
    singleOf(::ApiService)
}

val firebaseSharedModule: Module = module {
    single<FirebaseFirestore> { Firebase.firestore }
    singleOf(::FirestoreService)

}

val repositorySharedModule: Module = module {
    single<ClientNetworkRepository> { ClientNetworkRepositoryImpl(get(), get()) }
}

val useCaseSharedModule: Module = module {
    factoryOf(::GetClientsUseCase)
    factoryOf(::DeleteClientUseCase)
    factoryOf(::UpdateClientUseCase)
    factoryOf(::SendClientUseCase)
}

val viewModelSharedModule: Module = module {
    viewModelOf(::RegistrationViewModel)
    viewModelOf(::ConsultationViewModel)
}

val utilsSharedModule: Module = module {
    single<ConnectionUtils> { ConnectionUtilsImpl() }
}

fun initKoin(
    config: KoinAppDeclaration? = null
) {
    startKoin {
        config?.invoke(this)
        modules(viewModelSharedModule, useCaseSharedModule, repositorySharedModule, newtworkSharedModule, firebaseSharedModule, utilsSharedModule)
    }
}
