package com.coroutinelab.data.dto.emaillist


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmailListItemDto(
    @SerialName("hasAttachments")
    val hasAttachments: Boolean?,
    @SerialName("id")
    val id: String?,
    @SerialName("isImportant")
    val isImportant: Boolean?,
    @SerialName("isPromotional")
    val isPromotional: Boolean?,
    @SerialName("payload")
    val payload: Payload,
    @SerialName("snippet")
    val snippet: String?,
    @SerialName("subject")
    val subject: String?,
    @SerialName("threadId")
    val threadId: String?,
    @SerialName("timestamp")
    val timestamp: Int?
)