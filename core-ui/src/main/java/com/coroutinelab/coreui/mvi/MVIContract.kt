package com.coroutinelab.coreui.mvi

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface MVIContractEvent<EVENT>{
    fun event(event:EVENT)
}
interface MVIContract <STATE, EFFECT, EVENT> : MVIContractEvent<EVENT> {
    val state: StateFlow<STATE>
    val effect: SharedFlow<EFFECT>
}