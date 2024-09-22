package com.coroutinelab.presentation.emaillist


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coroutinelab.coreui.component.EmailItem
import com.coroutinelab.coreui.component.FullScreenError
import com.coroutinelab.coreui.component.LinearFullScreenProgress
import com.coroutinelab.coreui.functional.toFormattedDate
import com.coroutinelab.domain.model.emaillist.EmailListItemModel
import com.coroutinelab.presentation.emaillist.mvi.EmailListContract
import com.coroutinelab.presentation.emaillist.mvi.EmailListViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EmailListScreen(
    viewModel: EmailListViewModel = hiltViewModel(),
    onItemClick: (EmailListItemModel) -> Unit
) {
    val states = viewModel.state.collectAsState().value
    val dispatch: (EmailListContract.EmailListEvent) -> Unit = { event ->
        viewModel.event(event)
    }

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest {
            when (it) {
                is EmailListContract.EmailListEffect.NavigateToEmailDetails -> onItemClick(it.model)
            }
        }
    }


    when (states) {
        is EmailListContract.EmailListState.Error -> FullScreenError(errorMessage = states.error.toString())
        EmailListContract.EmailListState.Loading -> LinearFullScreenProgress()
        is EmailListContract.EmailListState.Success -> EmailListUi(states, dispatch)
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
                modifier = Modifier.padding(vertical = 8.dp).clickable {
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