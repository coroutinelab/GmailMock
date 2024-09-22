package com.coroutinelab.coreui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CircularProfileImage(
    modifier: Modifier,
    imageSource: String,
    size: Dp = 32.dp
) {
    GlideImage(
        model = imageSource,
        contentDescription = null,
        modifier =
        modifier.size(size)
            .background(
                color = Color.Gray,
                shape = CircleShape
            )
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
fun CircularProfileImagePreview() {
    CircularProfileImage(
        modifier = Modifier,
        imageSource = "https://i.pravatar.cc/250?img=5"
    )
}