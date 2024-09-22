package com.coroutinelab.coreui.functional

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun <T> Flow<T>.stateInWhileActive(
    scope: CoroutineScope,
    initial: T,
    preFetch: () -> Unit
): StateFlow<T> {
    return onStart {
        preFetch()
    }.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = initial
    )
}

fun String.getInitials() : String {
    return split(" ")
        .mapNotNull { it.firstOrNull() ?.uppercase() }
        .joinToString("")
}

fun String.toFormattedDate(): String {
    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    input.timeZone = TimeZone.getTimeZone("UTC")
    val output = SimpleDateFormat("MMM dd", Locale.getDefault())

    return try {
        input.parse(this)?.let { output.format(it) }.orEmpty()
    } catch (e:Exception){
       ""
    }
}
