package com.coroutinelab.data.mapper

import com.coroutinelab.data.dto.emaillist.EmailListItemDto
import com.coroutinelab.data.dto.emaillist.Payload
import com.coroutinelab.domain.model.emaillist.EmailListItemModel
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmailListMapperTest {
    private lateinit var mapper: EmailListMapper

    @BeforeEach
    fun setUp() {
        mapper = EmailListMapper()
    }

    @ParameterizedTest
    @MethodSource("provideDataForMapper")
    fun `Given empty input WHEN mapped THEN output is empty`(
        input: List<EmailListItemDto>,
        output: List<EmailListItemModel>
    ) {
        val result = mapper.map(input)

        result shouldBe output
    }

    private companion object {

        const val TEST_ID = "id"

        @JvmStatic
        fun provideDataForMapper(): Stream<Arguments> = Stream.of(
            testDataWithEmptyInputOutput(),
            testDataWithNullId(),
            testDataWithNullPayloadFrom(),
            testDataWithValidInput(),
            testDataToCheckTheDefaultValue(),
            testDataToCheckTheSorting()
        )

        private fun getEmailListItemDto(
            hasAttachments: Boolean? = null,
            id: String? = null,
            isImportant: Boolean? = null,
            isPromotional: Boolean? = null,
            payload: Payload = getPayload(),
            snippet: String? = null,
            subject: String? = null,
            threadId: String? = null,
            timestamp: Int? = null
        ) = EmailListItemDto(
            id = id,
            isImportant = isImportant,
            isPromotional = isPromotional,
            payload = payload,
            snippet = snippet,
            subject = subject,
            threadId = threadId,
            timestamp = timestamp,
            hasAttachments = hasAttachments
        )

        private fun testDataToCheckTheSorting(): Arguments = Arguments.of(
            listOf(
                getEmailListItemDto(
                    id = TEST_ID,
                    payload = getPayload(from = "from", subject = "subject", date = "1"),
                    snippet = null,
                    subject = "subject",
                    timestamp = 1,
                    threadId = "threadId",
                    isImportant = true,
                    isPromotional = true,
                    hasAttachments = true
                ),
                getEmailListItemDto(
                    id = TEST_ID,
                    payload = getPayload(from = "from", subject = "subject2", date = "2"),
                    snippet = null,
                    subject = "subject2",
                    timestamp = 2,
                    threadId = "threadId",
                    isImportant = true,
                    isPromotional = true,
                    hasAttachments = true
                )
            ), listOf(
                EmailListItemModel(
                    id = TEST_ID,
                    from = "from",
                    subject = "subject2",
                    profileImage = null,
                    snippet = "",
                    date = "2",
                    isImportant = true,
                    isStarred = true,
                    isPromotional = true
                ),
                EmailListItemModel(
                    id = TEST_ID,
                    from = "from",
                    subject = "subject",
                    profileImage = null,
                    snippet = "",
                    date = "1",
                    isImportant = true,
                    isStarred = true,
                    isPromotional = true
                )
            )
        )

        private fun testDataToCheckTheDefaultValue() = Arguments.of(
            listOf(
                getEmailListItemDto(
                    id = TEST_ID,
                    payload = getPayload(from = "from", subject = null, date = null),
                    snippet = null,
                    subject = "subject",
                    timestamp = 1,
                    threadId = "threadId",
                    isImportant = true,
                    isPromotional = true,
                    hasAttachments = true
                )
            ), listOf(
                EmailListItemModel(
                    id = TEST_ID,
                    from = "from",
                    subject = "",
                    profileImage = null,
                    snippet = "",
                    date = "",
                    isImportant = true,
                    isStarred = true,
                    isPromotional = true
                )
            )
        )

        private fun testDataWithValidInput() = Arguments.of(
            listOf(
                getEmailListItemDto(
                    id = TEST_ID,
                    payload = getPayload(from = "from", subject = "subject", date = "1"),
                    snippet = "snippet",
                    subject = "subject",
                    timestamp = 1,
                    threadId = "threadId",
                    isImportant = true,
                    isPromotional = true,
                    hasAttachments = true
                )
            ), listOf(
                EmailListItemModel(
                    id = TEST_ID,
                    from = "from",
                    subject = "subject",
                    profileImage = null,
                    snippet = "snippet",
                    date = "1",
                    isImportant = true,
                    isStarred = true,
                    isPromotional = true
                )
            )
        )

        private fun testDataWithNullPayloadFrom() = Arguments.of(
            listOf(getEmailListItemDto(id = TEST_ID)),
            emptyList<EmailListItemModel>()
        )

        private fun testDataWithNullId() =  Arguments.of(listOf(getEmailListItemDto()), emptyList<EmailListItemModel>())

        private fun testDataWithEmptyInputOutput() = Arguments.of(emptyList<EmailListItemDto>(), emptyList<EmailListItemModel>())

        private fun getPayload(
            from: String? = null,
            subject: String? = null,
            date: String? = null,
            profileImage: String? = null,
            email: String? = null
        ) = Payload(
            from = from,
            subject = subject,
            date = date,
            profileImage = profileImage,
            email = email
        )
    }

}