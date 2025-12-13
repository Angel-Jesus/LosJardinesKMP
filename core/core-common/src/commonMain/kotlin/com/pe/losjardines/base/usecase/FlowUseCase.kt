package com.pe.losjardines.base.usecase

import kotlinx.coroutines.flow.Flow

interface FlowUseCase<in P, out R>{
    fun execute(params: P): Flow<R>
}