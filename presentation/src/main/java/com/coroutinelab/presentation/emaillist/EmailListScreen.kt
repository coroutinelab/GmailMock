package com.coroutinelab.presentation.emaillist


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.coroutinelab.core.error.getErrorMessage
import com.coroutinelab.coreui.component.EmailItem
import com.coroutinelab.coreui.component.FullScreenError
import com.coroutinelab.coreui.component.LinearFullScreenProgress
import com.coroutinelab.coreui.functional.toFormattedDate
import com.coroutinelab.domain.model.emaillist.EmailListItemModel
import com.coroutinelab.presentation.emaillist.mvi.EmailListContract

@Composable
fun EmailListScreen(
    state: EmailListContract.EmailListState,
    effect: EmailListContract.EmailListEffect?,
    dispatch: (EmailListContract.EmailListEvent) -> Unit,
    onItemClick: (EmailListItemModel) -> Unit,
) {


    LaunchedEffect(key1 = effect) {
        effect?.let {
            when (it) {
                is EmailListContract.EmailListEffect.NavigateToEmailDetails -> onItemClick(it.model)
            }
        }
    }


    when (state) {
        is EmailListContract.EmailListState.Error -> FullScreenError(
            errorMessage = state.error.getErrorMessage()
        )

        EmailListContract.EmailListState.Loading -> LinearFullScreenProgress(
            modifier = Modifier.semantics {
                contentDescription = "Loading"
            })

        is EmailListContract.EmailListState.Success -> EmailListUi(state, dispatch)
    }
}

@Composable
fun EmailListUi(
    states: EmailListContract.EmailListState.Success,
    dispatch: (EmailListContract.EmailListEvent) -> Unit,

    ) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(
            states.emailList,
            key = { it.id }
        ) {
            EmailItem(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clickable {
                        dispatch.invoke(EmailListContract.EmailListEvent.EmailClicked(it))
                    },
                profileImageUrl = it.profileImage,
                senderName = it.from,
                emailSubject = it.subject,
                emailSnippet = it.snippet,
                isStarred = it.isStarred,
                date = it.date.toFormattedDate()
            )
        }
    }
}