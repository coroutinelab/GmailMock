package com.coroutinelab.data.dto.emaillist


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    @SerialName("date")
    val date: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("from")
    val from: String?,
    @SerialName("profileImage")
    val profileImage: String?,
    @SerialName("subject")
    val subject: String?
)