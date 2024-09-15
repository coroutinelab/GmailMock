package com.coroutinelab.data.dto.emaildetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmailDetailsDto(
    @SerialName("body")
    val body: Body?,
    @SerialName("hasAttachments")
    val hasAttachments: Boolean?,
    @SerialName("id")
    val id: String,
    @SerialName("isImportant")
    val isImportant: Boolean?,
    @SerialName("isPromotional")
    val isPromotional: Boolean?,
    @SerialName("labels")
    val labels: List<String?>,
    @SerialName("payload")
    val payload: Payload,
    @SerialName("snippet")
    val snippet: String?,
    @SerialName("threadId")
    val threadId: String?,
    @SerialName("timestamp")
    val timestamp: Int?
)