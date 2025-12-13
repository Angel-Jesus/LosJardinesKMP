package com.pe.losjardines.base.usecase

import kotlinx.coroutines.flow.Flow

abstract class BaseFlowUseCase<in Params, out Type> : FlowUseCase<Params, Type> {
    protected abstract fun run(params: Params): Flow<Type>

    override fun execute(params: Params): Flow<Type> = run(params)
}