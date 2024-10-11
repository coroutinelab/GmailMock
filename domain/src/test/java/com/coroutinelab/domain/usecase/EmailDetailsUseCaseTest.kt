package com.coroutinelab.domain.usecase


import com.coroutinelab.core.error.Failure
import com.coroutinelab.core.functional.Either
import com.coroutinelab.domain.model.emaildetails.EmailDetailsModel
import com.coroutinelab.domain.respository.EmailRepository
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import java.io.IOException

class EmailDetailsUseCaseTest: ShouldSpec({
    val repository: EmailRepository = mockk()
    val useCase = EmailDetailsUseCase(repository)

    should("return NetworkError for IOException") {
        val exception = IOException("Network Error")
        coEvery { repository.getEmailDetails() } returns Either.Left(Failure.NetworkError(exception))

        val result = useCase.invoke()

        result shouldBe Either.Left(Failure.NetworkError(exception))
    }

    should("return ServerError for HttpException") {
        coEvery { repository.getEmailDetails() } returns  Either.Left(Failure.ServerError(200,"server"))

        val result = useCase.invoke()

        result shouldBe Either.Left(Failure.ServerError(200, "server"))
    }

    should("return email details on success") {
        val emailDetails = mockk<EmailDetailsModel>()
        coEvery { repository.getEmailDetails() } returns Either.Right(emailDetails)

        val result = useCase.invoke()

        result shouldBe Either.Right(emailDetails)
    }
})

