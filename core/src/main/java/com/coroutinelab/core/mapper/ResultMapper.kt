package com.coroutinelab.core.mapper

fun interface ResultMapper<T, R> {
    fun map(input: T): R
}