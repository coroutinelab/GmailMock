package com.coroutinelab.core.functional

fun Boolean?.orDefault(default : Boolean = false) : Boolean = this ?: default

fun <T,R : Any> List<T>?.mapOrDefault(defaultListValue : List<R> = emptyList(), transform: (T) -> R): List<R> {
    return this?.filterNotNull()?.map(transform) ?: defaultListValue
}