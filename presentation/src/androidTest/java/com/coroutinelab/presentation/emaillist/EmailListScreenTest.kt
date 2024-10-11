package com.coroutinelab.presentation.emaillist

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.coroutinelab.core.error.Failure
import com.coroutinelab.domain.model.emaildetails.EmailDetailsModel
import com.coroutinelab.domain.model.emaildetails.SenderInfoModel
import com.coroutinelab.domain.model.emaillist.EmailListItemModel
import com.coroutinelab.presentation.emaildetails.EmailDetailsScreen
import com.coroutinelab.presentation.emaildetails.mvi.EmailDetailsContract
import com.coroutinelab.presentation.emaillist.mvi.EmailListContract
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class EmailListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testErrorState() {
        val state = EmailListContract.EmailListState.Error(
            Failure.ServerError(500, "Server Error")
        )
        val effect: EmailListContract.EmailListEffect? = null
        val dispatch: (EmailListContract.EmailListEvent) -> Unit = {}

        composeTestRule.setContent {
            EmailListScreen(
                state = state,
                effect = effect,
                dispatch = dispatch,
                onItemClick = {}
            )
        }

        composeTestRule.onNodeWithText("Server Error").isDisplayed()
    }

    @Test
    fun testSuccessState() {
        val emailList = listOf(
            EmailListItemModel(
                id = "1",
                from = "test@example.com",
                subject = "Test Subject",
                snippet = "Test Snippet",
                isStarred = false,
                date = "o",
                profileImage = "",
                isPromotional = false,
                isImportant = false
            ),
            EmailListItemModel(
                id = "2",
                from = "test@example.com",
                subject = "Test Subject 2",
                snippet = "Test Snippet",
                isStarred = false,
                date = "o",
                profileImage = "",
                isPromotional = false,
                isImportant = true
            ),
        )
        val state = EmailListContract.EmailListState.Success(emailList)
        val effect: EmailListContract.EmailListEffect? = null
        val dispatch: (EmailListContract.EmailListEvent) -> Unit = {}

        composeTestRule.setContent {
            EmailListScreen(
                state = state,
                effect = effect,
                dispatch = dispatch,
                onItemClick = {}
            )
        }

        with(composeTestRule) {
            onNodeWithText("Test Subject").assertExists()
            onNodeWithText("Test Subject").assert(hasClickAction())
            onNodeWithText("Test Subject 2").assertExists()
            onNodeWithText("Test Subject 2").assert(hasClickAction())
        }
    }

    @Test
    fun testLoadingState() {
        val state = EmailListContract.EmailListState.Loading
        val effect: EmailListContract.EmailListEffect? = null
        val dispatch: (EmailListContract.EmailListEvent) -> Unit = {}

        composeTestRule.setContent {
            EmailListScreen(
                state = state,
                effect = effect,
                dispatch = dispatch,
                onItemClick = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Loading").assertExists()
    }

    @Test
    fun testEmailItemClickOpensEmailDetailsScreen() {
        val emailItem = EmailListItemModel(
            id = "id",
            from = "test",
            profileImage = "",
            subject = "Test Subject",
            snippet = "Snippet",
            isStarred = false,
            date = "date",
            isPromotional = true,
            isImportant = true
        )

        composeTestRule.setContent {
            EmailListScreen(
                state = EmailListContract.EmailListState.Success(listOf(emailItem)),
                effect = null,
                dispatch = {},
                onItemClick = { email ->
                    composeTestRule.setContent {
                        EmailDetailsScreen(
                            from = email.from,
                            profileImage = email.profileImage,
                            subject = email.subject,
                            isPromotional = true,
                            state = EmailDetailsContract.UIState(
                                isLoading = false,
                                isError = false,
                                details = EmailDetailsModel(
                                    from = SenderInfoModel("m", ""),
                                    subject = email.subject,
                                    date = email.date,
                                    isStarred = email.isStarred,
                                    isImportant = true,
                                    isPromotional = true,
                                    htmlBody = "htmlBody",
                                    cc = emptyList(),
                                    bcc = emptyList(),
                                    id = email.id,
                                    to = emptyList(),
                                    labels = emptyList(),
                                    fileInfo = emptyList()
                                )
                            )
                        )
                    }
                }
            )
        }

        // Simulate click on the email item
        composeTestRule.onNodeWithText("Test Subject").performClick()

        // Verify that the email details screen is displayed
        composeTestRule.onNodeWithText("Test Subject").assertExists()
        composeTestRule.onNodeWithText("Unsubscribe").assertExists()
    }
}

