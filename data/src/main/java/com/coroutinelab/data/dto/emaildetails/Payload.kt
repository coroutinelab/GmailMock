package com.coroutinelab.data.dto.emaildetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    @SerialName("attachments")
    val attachments: List<Attachment?>,
    @SerialName("bcc")
    val bcc: List<RecipientInfo>?,
    @SerialName("cc")
    val cc: List<RecipientInfo>?,
    @SerialName("date")
    val date: String?,
    @SerialName("from")
    val senderInfo: SenderInfo,
    @SerialName("subject")
    val subject: String?,
    @SerialName("to")
    val to: List<RecipientInfo>
)