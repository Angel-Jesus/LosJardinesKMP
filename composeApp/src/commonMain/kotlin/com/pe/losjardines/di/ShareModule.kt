package com.pe.losjardines.di

import com.pe.losjardines.presentation.viewmodel.RegistrationViewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

/*val RepositorySharedModule: Module = module {
    single {  }
}*/

val viewModelSharedModule: Module = module {
    viewModelOf(::RegistrationViewModel)
}

fun initKoin(
    config: KoinAppDeclaration? = null
) {
    startKoin {
        config?.invoke(this)
        modules(viewModelSharedModule)
    }
}
