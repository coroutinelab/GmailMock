package com.coroutinelab.data.mapper

import com.coroutinelab.data.dto.emaildetails.Attachment
import com.coroutinelab.data.dto.emaildetails.Body
import com.coroutinelab.data.dto.emaildetails.EmailDetailsDto
import com.coroutinelab.data.dto.emaildetails.Payload
import com.coroutinelab.data.dto.emaildetails.RecipientInfo
import com.coroutinelab.data.dto.emaildetails.SenderInfo
import com.coroutinelab.domain.model.emaildetails.EmailDetailsModel
import com.coroutinelab.domain.model.emaildetails.FileInfo
import com.coroutinelab.domain.model.emaildetails.RecipientModel
import com.coroutinelab.domain.model.emaildetails.SenderInfoModel
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmailDetailsMapperTest {

    private lateinit var mapper: EmailDetailsMapper

    @BeforeEach
    fun setUp() {
        mapper = EmailDetailsMapper()
    }

    @ParameterizedTest
    @MethodSource("provideDataForMapper")
    fun `GIVEN EmailDetailsDto WHEN mapped Then return EmailDetailsModel`(
        input: List<EmailDetailsDto>,
        output: EmailDetailsModel
    ){
        val result = mapper.map(ArrayList(input))

        result shouldBe output
    }

    private fun provideDataForMapper(): Stream<Arguments>  = Stream.of(
        testDataToCheckTheDefaultValue(),
        testDataToCheckTheEmptyValue(),
        testDataToCheckTheEmptyHtmlValue(),
        testDataToCheckTheFileInfoValue(),
        testDataToCheckTheDefaultFileInfoValue(),
    )

    private fun testDataToCheckTheFileInfoValue() = Arguments.of(
        listOf(EmailDetailsDto(
            id = "id",
            payload = Payload(
                senderInfo = SenderInfo(
                    email = "email",
                    name = "name",
                    profileImage = "profileImage"
                ),
                to = emptyList(),
                cc = emptyList(),
                bcc = emptyList(),
                subject = null,
                date = null,
                attachments = listOf(Attachment(
                    filename = "filename",
                    mimeType = "mimeType",
                    size = 123,
                    downloadUrl = "downloadUrl"
                ))
            ),
            body = Body(
                html = null,
                text = "text"
            ),
            isImportant = true,
            labels = listOf(null),
            isPromotional = true,
            hasAttachments = true,
            snippet = "snippet",
            threadId = "threadId",
            timestamp = 123
        )), EmailDetailsModel(
            id = "id",
            from = SenderInfoModel(
                email = "email",
                name = "name",
                profileImage = "profileImage"
            ),
            to = emptyList(),
            cc = emptyList(),
            bcc = emptyList(),
            subject = "",
            htmlBody = "text",
            date = "",
            isImportant = true,
            isStarred = false,
            isPromotional = true,
            fileInfo = listOf(
               FileInfo(
                    filename = "filename",
                    mimeType = "mimeType",
                    size = 123,
                    downLoadUrl = "downloadUrl"
                )
            ),
            labels = emptyList()
        )
    )

    private fun testDataToCheckTheDefaultFileInfoValue() = Arguments.of(
        listOf(EmailDetailsDto(
            id = "id",
            payload = Payload(
                senderInfo = SenderInfo(
                    email = "email",
                    name = "name",
                    profileImage = "profileImage"
                ),
                to = emptyList(),
                cc = emptyList(),
                bcc = emptyList(),
                subject = null,
                date = null,
                attachments = listOf(Attachment(
                    filename = null,
                    mimeType = null,
                    size = null,
                    downloadUrl = null
                ))
            ),
            body = Body(
                html = null,
                text = "text"
            ),
            isImportant = true,
            labels = listOf(null),
            isPromotional = true,
            hasAttachments = true,
            snippet = "snippet",
            threadId = "threadId",
            timestamp = 123
        )), EmailDetailsModel(
            id = "id",
            from = SenderInfoModel(
                email = "email",
                name = "name",
                profileImage = "profileImage"
            ),
            to = emptyList(),
            cc = emptyList(),
            bcc = emptyList(),
            subject = "",
            htmlBody = "text",
            date = "",
            isImportant = true,
            isStarred = false,
            isPromotional = true,
            fileInfo = listOf(
               FileInfo(
                    filename = "",
                    mimeType = "",
                    size = 0,
                    downLoadUrl = null
                )
            ),
            labels = emptyList()
        )
    )

    private fun testDataToCheckTheEmptyValue() = Arguments.of(
        listOf(EmailDetailsDto(
            id = "id",
            payload = Payload(
                senderInfo = SenderInfo(
                    email = "email",
                    name = "name",
                    profileImage = "profileImage"
                ),
                to = emptyList(),
                cc = emptyList(),
                bcc = emptyList(),
                subject = null,
                date = null,
                attachments = listOf(null)
            ),
            body = Body(
                html = null,
                text = "text"
            ),
            isImportant = true,
            labels = listOf(null),
            isPromotional = true,
            hasAttachments = true,
            snippet = "snippet",
            threadId = "threadId",
            timestamp = 123
        )), EmailDetailsModel(
            id = "id",
            from = SenderInfoModel(
                email = "email",
                name = "name",
                profileImage = "profileImage"
            ),
            to = emptyList(),
            cc = emptyList(),
            bcc = emptyList(),
            subject = "",
            htmlBody = "text",
            date = "",
            isImportant = true,
            isStarred = false,
            isPromotional = true,
            fileInfo = emptyList(),
            labels = emptyList()
        )
    )

    private fun testDataToCheckTheEmptyHtmlValue() = Arguments.of(
        listOf(EmailDetailsDto(
            id = "id",
            payload = Payload(
                senderInfo = SenderInfo(
                    email = "email",
                    name = "name",
                    profileImage = "profileImage"
                ),
                to = emptyList(),
                cc = emptyList(),
                bcc = emptyList(),
                subject = null,
                date = null,
                attachments = listOf(null)
            ),
            body = null,
            isImportant = true,
            labels = listOf(null),
            isPromotional = true,
            hasAttachments = true,
            snippet = "snippet",
            threadId = "threadId",
            timestamp = 123
        )), EmailDetailsModel(
            id = "id",
            from = SenderInfoModel(
                email = "email",
                name = "name",
                profileImage = "profileImage"
            ),
            to = emptyList(),
            cc = emptyList(),
            bcc = emptyList(),
            subject = "",
            htmlBody = "",
            date = "",
            isImportant = true,
            isStarred = false,
            isPromotional = true,
            fileInfo = emptyList(),
            labels = emptyList()
        )
    )

    private fun testDataToCheckTheDefaultValue() = Arguments.of(
        listOf(EmailDetailsDto(
            id = "id",
            payload = Payload(
                senderInfo = SenderInfo(
                    email = "email",
                    name = "name",
                    profileImage = "profileImage"
                ),
                to = listOf(
                    RecipientInfo(
                        email = "email to",
                        name = "name to"
                    )
                ),
                cc = listOf(
                    RecipientInfo(
                        email = "email cc",
                        name = "name cc"
                    )
                ),
                bcc = listOf(
                    RecipientInfo(
                        email = "email bcc",
                        name = "name bcc"
                    )
                ),
                subject = "subject",
                date = "date",
                attachments = emptyList()
            ),
            body = Body(
                html = "html",
                text = "text"
            ),
            isImportant = true,
            labels = listOf("Starred"),
            isPromotional = true,
            hasAttachments = true,
            snippet = "snippet",
            threadId = "threadId",
            timestamp = 123
        )), EmailDetailsModel(
            id = "id",
            from = SenderInfoModel(
                email = "email",
                name = "name",
                profileImage = "profileImage"
            ),
            to = listOf(
                RecipientModel(
                    email = "email to",
                    name = "name to"
                )
            ),
            cc = listOf(
                RecipientModel(
                    email = "email cc",
                    name = "name cc"
                )
            ),
            bcc = listOf(
                RecipientModel(
                    email = "email bcc",
                    name = "name bcc"
                )
            ),
            subject = "subject",
            htmlBody = "html",
            date = "date",
            isImportant = true,
            isStarred = true,
            isPromotional = true,
            fileInfo = emptyList(),
            labels = listOf("Starred")
        )
    )
}