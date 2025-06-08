package com.pe.losjardines.presentation.viewmodel

import com.pe.losjardines.core.base.error.Failure
import com.pe.losjardines.core.base.ui.BaseViewModel
import com.pe.losjardines.domain.model.ClientModel
import com.pe.losjardines.domain.usecase.GetClientsUseCase
import com.pe.losjardines.presentation.contract.consult.ConsultEffect
import com.pe.losjardines.presentation.contract.consult.ConsultEvent
import com.pe.losjardines.presentation.contract.consult.ConsultState
import com.pe.losjardines.domain.model.ClientFilter
import com.pe.losjardines.domain.usecase.DeleteClientUseCase
import com.pe.losjardines.domain.usecase.UpdateClientUseCase
import com.pe.losjardines.presentation.enums.StateConsult
import com.pe.losjardines.presentation.model.DataUpdate

class ConsultationViewModel(
    private val getClientsUseCase: GetClientsUseCase,
    private val deleteClientUseCase: DeleteClientUseCase,
    private val updateClientUseCase: UpdateClientUseCase
): BaseViewModel<ConsultState, ConsultEvent, ConsultEffect>(
    initialState = ConsultState()
) {

    override fun onEvent(event: ConsultEvent) {
        when(event){
            is ConsultEvent.GetClient -> getClient(event.filter)
            is ConsultEvent.DeleteForm -> deleteClient(event.id)
            is ConsultEvent.UpdateForm -> dataUpdate(event.dataUpdate)
        }
    }

    private fun getClient(filter: ClientFilter){
        updateState { copy(state = StateConsult.LOADING, clients = emptyList()) }
        executeUseCase(
            useCase = getClientsUseCase,
            params = GetClientsUseCase.Params(filter),
            onSuccess = ::handleSuccessGetClient,
            onError = ::handleErrorGetClient
        )
    }

    private fun handleSuccessGetClient(clients: List<ClientModel>){
        updateState { copy(clients = clients, state = StateConsult.SUCCESS) }
    }

    private fun handleErrorGetClient(failure: Failure){
        println(failure)
        updateState { copy(state = StateConsult.ERROR, clients = emptyList()) }
    }

    private fun deleteClient(id: Int){
        updateState { copy(state = StateConsult.LOADING) }
        executeUseCase(
            useCase = deleteClientUseCase,
            params = DeleteClientUseCase.Params(id),
            onSuccess = ::handleSuccessDeleteClient,
            onError = ::handleErrorDeleteClient
        )
    }

    private fun handleSuccessDeleteClient(status: Boolean){
        getClient(ClientFilter.None)
    }

    private fun handleErrorDeleteClient(failure: Failure){
        println(failure)
    }

    private fun dataUpdate(dataUpdate: DataUpdate){
        updateState { copy(state = StateConsult.LOADING) }
        executeUseCase(
            useCase = updateClientUseCase,
            params = UpdateClientUseCase.Params(dataUpdate),
            onSuccess = ::handleSuccessUpdateClient,
            onError = ::handleErrorUpdateClient
        )
    }

    private fun handleSuccessUpdateClient(status: Boolean){
        getClient(ClientFilter.None)
    }

    private fun handleErrorUpdateClient(failure: Failure){
        println(failure)
    }

}