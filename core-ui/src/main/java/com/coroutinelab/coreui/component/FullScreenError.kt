package com.coroutinelab.coreui.component


import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coroutinelab.coreui.R

@Composable
fun FullScreenError(
    errorMessage: String,
    @DrawableRes errorMessageIcon: Int = R.drawable.alert_error_icon
)  {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(errorMessageIcon),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
        )

        Text(
            modifier = Modifier.padding(16.dp),
            text = errorMessage,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview
@Composable
fun FullScreenErrorPreview()  {
    Surface {
        FullScreenError(
            errorMessage = "Lorem Ipsum",
            errorMessageIcon = R.drawable.alert_error_icon
        )
    }
}