package com.coroutinelab.data.repository

import com.coroutinelab.core.error.Failure
import com.coroutinelab.core.functional.Either
import com.coroutinelab.data.dto.emaildetails.Body
import com.coroutinelab.data.dto.emaildetails.EmailDetailsDto
import com.coroutinelab.data.dto.emaildetails.SenderInfo
import com.coroutinelab.data.dto.emaillist.EmailListItemDto
import com.coroutinelab.data.dto.emaillist.Payload
import com.coroutinelab.data.mapper.EmailDetailsMapper
import com.coroutinelab.data.mapper.EmailListMapper
import com.coroutinelab.data.remote.api.ApiService
import com.coroutinelab.data.remote.handler.safeApiCall
import com.coroutinelab.domain.model.emaillist.EmailListItemModel
import com.coroutinelab.domain.respository.EmailRepository
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import javax.inject.Inject

class EmailRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val emailListMapper: EmailListMapper,
    private val emailDetailsMapper: EmailDetailsMapper
) : EmailRepository {
    override suspend fun getEmailList(): Either<Failure, List<EmailListItemModel>> = safeApiCall(
        apiCall = { apiService.getEmailList() },
        mapper = { emailListMapper.map(it) }
    )

    override suspend fun getEmailDetails() = safeApiCall(
        apiCall = { apiService.getEmailDetail() },
        mapper = { emailDetailsMapper.map(it) }
    )
}

/*
fun main() =
    runBlocking {
        // Create mock instances
        val apiService = MockApiService()
        val emailListMapper = EmailListMapper()
        val emailDetailsMapper = EmailDetailsMapper()

        // Initialize EmailRepositoryImpl with mock dependencies
        val emailRepository = EmailRepositoryImpl(apiService, emailListMapper, emailDetailsMapper)

        // Test getEmailList
        val emailListResult = emailRepository.getEmailList()
        println("Email List Result: $emailListResult")

        // Test getEmailDetails
        val emailDetailsResult = emailRepository.getEmailDetails()
        println("Email Details Result: $emailDetailsResult")
    }

class MockApiService : ApiService {
    override suspend fun getEmailList(): Response<ArrayList<EmailListItemDto>> {
        val mockEmailList = arrayListOf(
            EmailListItemDto(
                hasAttachments = false,
                id = "1",
                isImportant = true,
                isPromotional = false,
                payload = Payload("", "", "Coroutine lab", "", ""),
                snippet = "Mock snippet",
                subject = "Mock subject",
                threadId = "thread-1",
                timestamp = 1
            )
        )
        return Response.success(mockEmailList)
    }

    override suspend fun getEmailDetail(): Response<ArrayList<EmailDetailsDto>> {
        val mockEmailDetails = arrayListOf(
            EmailDetailsDto(
                id = "1",
                body = Body("", ""),
                timestamp = 2,
                isPromotional = false,
                hasAttachments = false,
                isImportant = false,
                labels = emptyList(),
                snippet = "",
                threadId = "",
                payload = com.coroutinelab.data.dto.emaildetails.Payload(
                    date = "",
                    attachments = emptyList(),
                    subject = "",
                    cc = emptyList(),
                    to = emptyList(),
                    bcc = emptyList(),
                    senderInfo = SenderInfo(
                        profileImage = "",
                        email = "coroutinelab@test.com",
                        name = "coroutinelab"
                    )
                )
            )
        )
        return Response.success(mockEmailDetails)
    }
}
*/
