package com.coroutinelab.domain.usecase

import com.coroutinelab.core.error.Failure
import com.coroutinelab.core.functional.Either
import com.coroutinelab.domain.model.emaillist.EmailListItemModel
import com.coroutinelab.domain.respository.EmailRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

class EmailListUseCaseTest {

    private val repository: EmailRepository = mockk()
    private lateinit var useCase: EmailListUseCase

    @BeforeEach
    fun setUp() {
        useCase = EmailListUseCase(repository = repository)
    }

    @Test
    fun `GIVEN ioexception occurs WHEN use case is invoked THEN NetworkError returned`() = runTest {
        val exception = IOException("Network Error")

        coEvery { repository.getEmailList() } returns Either.Left(Failure.NetworkError(exception))

        assert(useCase.invoke() == Either.Left(Failure.NetworkError(exception)))
    }

    @Test
    fun `GIVEN HttpException occurs WHEN use case is invoked THEN ServerError returned`() = runTest {
        coEvery { repository.getEmailList() } returns Either.Left(Failure.ServerError(200,"server"))

        assert(useCase.invoke() == Either.Left(Failure.ServerError(200, "server")))
    }

    @Test
    fun `GIVEN successful response WHEN use case is invoked THEN list of EmailListItemModel returned`() = runTest {
        val emailDetails = mockk<EmailListItemModel>()

        coEvery { repository.getEmailList() } returns Either.Right(listOf(emailDetails))

        assert(useCase.invoke() == Either.Right(listOf(emailDetails)))
    }

}