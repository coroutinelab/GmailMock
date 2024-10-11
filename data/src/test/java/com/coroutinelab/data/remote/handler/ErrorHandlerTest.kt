package com.coroutinelab.data.remote.handler


import com.coroutinelab.core.error.Failure
import com.coroutinelab.core.functional.Either
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import retrofit2.HttpException
import java.io.IOException


class ErrorHandlerTest: StringSpec({

    "test toEither with IOException" {
        val exception = IOException("Network Error")
        val either = exception.toEither()
        either shouldBe Either.Left(Failure.NetworkError(exception))
    }

    "test toEither with Unknown Exception" {
        val exception = Exception("Unknown Error")
        val either = exception.toEither()
        either shouldBe Either.Left(Failure.UnknownError(exception))
    }

    "test toEither with HttpException" {
        val exception = mockk<HttpException>().apply {
            every { code() } returns 404
            every { message() } returns "Not found"
        }
        val either = exception.toEither()
        either shouldBe Either.Left(Failure.ServerError(404, "Not found"))
    }

})