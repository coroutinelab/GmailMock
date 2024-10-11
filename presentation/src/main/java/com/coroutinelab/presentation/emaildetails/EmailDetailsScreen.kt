package com.coroutinelab.presentation.emaildetails

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.coroutinelab.coreui.component.EmailDetailsBottomSection
import com.coroutinelab.coreui.component.EmailDetailsSenderInfo
import com.coroutinelab.coreui.component.EmailDetailsSubject
import com.coroutinelab.coreui.component.FullScreenError
import com.coroutinelab.coreui.component.LinearFullScreenProgress
import com.coroutinelab.domain.model.emaildetails.EmailDetailsModel
import com.coroutinelab.presentation.emaildetails.mvi.EmailDetailsContract

@Composable
fun EmailDetailsScreen(
    from: String,
    profileImage: String?,
    subject: String,
    isPromotional: Boolean,
    state: EmailDetailsContract.UIState
){
    if (state.isLoading) {
        LinearFullScreenProgress(modifier = Modifier.semantics {
            contentDescription = "Loading"
        })
    }

    if (state.isError) {
        FullScreenError(errorMessage = "Something went wrong")
    }

    state.details?.let {
        EmailDetailsUi(from, profileImage, subject, isPromotional, it)
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun EmailDetailsUi(
    from: String,
    profileImage: String?,
    subject: String,
    isPromotional: Boolean,
    model: EmailDetailsModel
) {
    val context =  LocalContext.current
    var isWebViewLoading by remember { mutableStateOf(true) }
    val webView = remember { WebView(context) }

    LaunchedEffect(model) {
        webView.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    isWebViewLoading = false
                }
            }
            settings.javaScriptEnabled = true
            loadData(model.htmlBody, "text/html", "UTF-8")
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(16.dp)
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    EmailDetailsSubject(
                        subjectText = subject,
                        tag = "Inbox",
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        imageVector = Icons.Outlined.Star,
                        tint = if (model.isStarred) Color(0xFFFFD700) else Color(0x706B6B6E),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(Modifier.height(16.dp))
                EmailDetailsSenderInfo(profileImageUrl = profileImage!!, isPromotional = isPromotional, from = from)
                Spacer(Modifier.height(16.dp))

                Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    if(isWebViewLoading){
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    } else{
                        AndroidView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState()),
                            factory = {
                                webView
                            }
                        )
                    }
                }
            }
        }

        EmailDetailsBottomSection()
    }
}











