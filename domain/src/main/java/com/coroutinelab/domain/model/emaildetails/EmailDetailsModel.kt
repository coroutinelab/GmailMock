package com.coroutinelab.domain.model.emaildetails


data class EmailDetailsModel(
    val id: String,
    val from: SenderInfoModel,
    val to: List<RecipientModel>,
    val cc: List<RecipientModel>,
    val bcc: List<RecipientModel>,
    val subject: String,
    val htmlBody: String? = null,
    val plainBody: String,
    val date: String,
    val isImportant: Boolean,
    val isStarred: Boolean,
    val isPromotional: Boolean,
    val fileInfo: List<FileInfo>,
    val labels: List<String>
)
