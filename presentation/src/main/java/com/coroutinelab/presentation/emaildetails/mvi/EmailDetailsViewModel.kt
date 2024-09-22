package com.coroutinelab.presentation.emaildetails.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coroutinelab.core.functional.fold
import com.coroutinelab.coreui.functional.stateInWhileActive
import com.coroutinelab.domain.usecase.EmailDetailsUseCase
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
class EmailDetailsViewModel @Inject constructor(
    private val detailsUseCase: EmailDetailsUseCase
): ViewModel() , EmailDetailsContract {

    private val mutableUIState: MutableStateFlow<EmailDetailsContract.UIState> =
        MutableStateFlow(EmailDetailsContract.UIState())

    private val mutableSharedFlow: MutableSharedFlow<EmailDetailsContract.EmailDetailsEffect> =
        MutableSharedFlow()

    override val state: StateFlow<EmailDetailsContract.UIState>
        get() = mutableUIState.stateInWhileActive(viewModelScope, EmailDetailsContract.UIState()){
            event(EmailDetailsContract.EmailDetailsEvent.LoadEmailDetails)
        }
    override val effect: SharedFlow<EmailDetailsContract.EmailDetailsEffect>
        get() = mutableSharedFlow.asSharedFlow()

    override fun event(event: EmailDetailsContract.EmailDetailsEvent) {
        when (event) {
            EmailDetailsContract.EmailDetailsEvent.ArchiveEmail -> TODO()
            EmailDetailsContract.EmailDetailsEvent.DeleteEmail -> TODO()
            EmailDetailsContract.EmailDetailsEvent.LoadEmailDetails -> loadEmailDetails()
            EmailDetailsContract.EmailDetailsEvent.NewEmail -> TODO()
            EmailDetailsContract.EmailDetailsEvent.OnAttachFile -> TODO()
            EmailDetailsContract.EmailDetailsEvent.OnBottomBarEmojiReply -> TODO()
            EmailDetailsContract.EmailDetailsEvent.OnBottomBarReply -> TODO()
            EmailDetailsContract.EmailDetailsEvent.OnEmailMenu -> TODO()
            EmailDetailsContract.EmailDetailsEvent.OnMarkAsImportant -> TODO()
            EmailDetailsContract.EmailDetailsEvent.OnReplyDropDown -> TODO()
            EmailDetailsContract.EmailDetailsEvent.OnSendEmojiReply -> TODO()
            EmailDetailsContract.EmailDetailsEvent.OnSendReply -> TODO()
            EmailDetailsContract.EmailDetailsEvent.OnTopAppBarEmailMenu -> TODO()
        }
    }

    private fun loadEmailDetails() {
        viewModelScope.launch {
            detailsUseCase().fold(
                {
                    mutableUIState.update { state ->
                        state.copy(details = null, isLoading = false, isError = true)
                    }
                },
                { mutableUIState.update { state ->
                    state.copy(details = it, isLoading = false)
                }}
            )
        }
    }

}









