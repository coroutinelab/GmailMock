package com.coroutinelab.domain.model.emaildetails

data class SenderInfoModel(
    val email: String,
    val name: String,
    val profileImage: String? = null
)
