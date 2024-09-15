package com.coroutinelab.domain.usecase

import com.coroutinelab.domain.respository.EmailRepository
import javax.inject.Inject

class EmailListUsecase @Inject constructor(
    private val repository: EmailRepository
) {
    suspend operator fun invoke() = repository.getEmailList()
}