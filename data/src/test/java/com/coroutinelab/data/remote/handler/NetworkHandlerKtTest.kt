package com.coroutinelab.data.remote.handler

import com.coroutinelab.core.error.Failure
import com.coroutinelab.core.functional.Either
import com.coroutinelab.core.mapper.ResultMapper
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import java.io.IOException

class NetworkHandlerKtTest : BehaviorSpec({
    val apiCall = mockk<suspend () -> Response<String>>()
    val mapper = mockk<ResultMapper<String, String>>()


    Given("a successful API call") {
        coEvery { apiCall() } returns Response.success("Success")
        coEvery { mapper.map("Success") } returns "MappedSuccess"

        When("safeApiCall is invoked") {
            val result = safeApiCall(Dispatchers.Unconfined, apiCall, mapper)

            Then("it should return the mapped result") {
                result shouldBe  Either.Right("MappedSuccess")
            }
        }
    }

    Given("an API call that returns an error response") {
        coEvery { apiCall() } returns Response.error(500, mockk(relaxed = true))

        When("safeApiCall is invoked") {
            val result = safeApiCall(Dispatchers.Unconfined, apiCall, mapper)

            Then("it should return a ServerError") {
                result shouldBe Either.Left(Failure.ServerError(500, "Response.error()"))
            }
        }
    }


    Given("an API call that throws an exception") {
        val exception = IOException("Network Error")
        coEvery { apiCall() } throws exception

        When("safeApiCall is invoked") {
            val result = safeApiCall(Dispatchers.Unconfined, apiCall, mapper)

            Then("it should return a NetworkError") {
                result shouldBe Either.Left(Failure.NetworkError(exception))
            }
        }
    }

    Given("body is null") {
        coEvery { apiCall() } returns Response.success(null)

        When("safeApiCall is invoked") {
            val result = safeApiCall(Dispatchers.Unconfined, apiCall, mapper)

            Then("it should return a ServerError") {
                result shouldBe Either.Left(Failure.ServerError(200, "OK"))
            }
        }
    }
})