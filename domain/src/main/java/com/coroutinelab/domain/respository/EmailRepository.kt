package com.coroutinelab.domain.respository

import com.coroutinelab.core.error.Failure
import com.coroutinelab.core.functional.Either
import com.coroutinelab.domain.model.emaildetails.EmailDetailsModel
import com.coroutinelab.domain.model.emaillist.EmailListItemModel

interface EmailRepository {
    suspend fun getEmailList() : Either<Failure, List<EmailListItemModel>>
    suspend fun getEmailDetails() : Either<Failure, EmailDetailsModel>
}