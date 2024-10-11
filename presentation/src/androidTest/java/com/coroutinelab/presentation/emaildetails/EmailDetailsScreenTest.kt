package com.coroutinelab.presentation.emaildetails

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coroutinelab.domain.model.emaildetails.EmailDetailsModel
import com.coroutinelab.domain.model.emaildetails.SenderInfoModel
import com.coroutinelab.presentation.emaildetails.mvi.EmailDetailsContract
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmailDetailsScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testErrorState() {
        composeTestRule.setContent {
            EmailDetailsScreen(
                from = "",
                profileImage = "",
                subject = "",
                isPromotional = false,
                state = EmailDetailsContract.UIState(
                    isLoading = false,
                    isError = true,
                    details = null
                )
            )
        }

        composeTestRule.onNodeWithText("Something went wrong").assertExists()
    }

    @Test
    fun testLoadingState() {
        composeTestRule.setContent {
            EmailDetailsScreen(
                from = "",
                profileImage = "",
                subject = "",
                isPromotional = false,
                state = EmailDetailsContract.UIState(
                    isLoading = true,
                    isError = false,
                    details = null
                )
            )
        }

        composeTestRule.onNodeWithContentDescription("Loading").assertExists()
    }

    @Test
    fun testSuccessWithNonPromotionalState() {
        composeTestRule.setContent {
            EmailDetailsScreen(
                from = "test",
                profileImage = "",
                subject = "subject",
                isPromotional = false,
                state = EmailDetailsContract.UIState(
                    isLoading = false, isError = false, details = EmailDetailsModel(
                        from = SenderInfoModel("test", ""),
                        subject = "subject",
                        date = "date",
                        isStarred = false,
                        isImportant = false,
                        isPromotional = true,
                        htmlBody = "htmlBody",
                        cc = emptyList(),
                        bcc = emptyList(),
                        id = "id",
                        to = emptyList(),
                        labels = emptyList(),
                        fileInfo = emptyList()
                    )
                )
            )
        }
    }

}
