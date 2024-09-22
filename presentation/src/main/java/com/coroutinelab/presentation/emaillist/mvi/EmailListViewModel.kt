package com.coroutinelab.presentation.emaillist.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutinelab.core.functional.fold
import com.coroutinelab.coreui.functional.stateInWhileActive
import com.coroutinelab.domain.usecase.EmailListUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailListViewModel @Inject constructor(
    private val getEmailsUseCase: EmailListUsecase
) : ViewModel(), EmailListContract {
    private val mutableUIState: MutableStateFlow<EmailListContract.EmailListState> =
        MutableStateFlow(EmailListContract.EmailListState.Loading)

    private val mutableSharedFlow: MutableSharedFlow<EmailListContract.EmailListEffect> =
        MutableSharedFlow()

    override val state: StateFlow<EmailListContract.EmailListState>
        get() = mutableUIState
            .stateInWhileActive(viewModelScope, EmailListContract.EmailListState.Loading){
                event(EmailListContract.EmailListEvent.LoadEmailList)
            }
    override val effect: SharedFlow<EmailListContract.EmailListEffect>
        get() = mutableSharedFlow.asSharedFlow()

    override fun event(event: EmailListContract.EmailListEvent){
        when (event) {
            is EmailListContract.EmailListEvent.LoadEmailList -> {
                loadEmail()
            }

            is EmailListContract.EmailListEvent.EmailClicked ->
                viewModelScope.launch {
                    mutableSharedFlow.emit(EmailListContract.EmailListEffect.NavigateToEmailDetails(event.model))
                }

        }
    }

    private fun loadEmail() {
        viewModelScope.launch {
            getEmailsUseCase().fold(
                { updateState( EmailListContract.EmailListState.Error(it)) },
                { updateState(EmailListContract.EmailListState.Success(emailList = it)) }
            )
        }
    }

    private fun updateState(state: EmailListContract.EmailListState) {
        mutableUIState.update { state }
    }

}
