package com.coroutinelab.domain.model.emaillist

data class EmailListItemModel(
    val id: String,
    val from: String,
    val profileImage: String? = null,
    val subject: String,
    val snippet: String,
    val date: String,
    val isImportant: Boolean,
    val isStarred: Boolean,
    val isPromotional: Boolean
)
