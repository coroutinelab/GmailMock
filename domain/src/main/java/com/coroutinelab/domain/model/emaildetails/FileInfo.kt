package com.coroutinelab.domain.model.emaildetails

data class FileInfo(
    val filename: String,
    val mimeType: String,
    val size: Long = 0L,
    val downLoadUrl: String? = null
)
