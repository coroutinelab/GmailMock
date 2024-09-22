package com.coroutinelab.coreui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.automirrored.outlined.Shortcut
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.coroutinelab.coreui.R

@Composable
fun EmailDetailsBottomSection(
    modifier: Modifier = Modifier,
    isReplyMode: Boolean = false
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier
                .size(30.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.AttachFile,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceContainerHighest, shape = RoundedCornerShape(24.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (isReplyMode) {
                ReplyBarWithInput()
            } else {
                ReplyBar()
            }
        }

        IconButton(
            onClick = {},
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Mood,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun ReplyBarWithInput() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column {
            Row {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp)
                        .scale(scaleX = -1f, scaleY = 1f)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Shortcut,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = Color.LightGray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Shortcut,
                        contentDescription = null,
                        modifier =
                        Modifier
                            .size(24.dp)
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(4.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.OpenInNew,
                        contentDescription = null,
                        modifier =
                        Modifier
                            .size(24.dp)
                    )
                }
            }
            TextField(
                value = "Reply",
                onValueChange = {  },
                placeholder = { Text(text = "Compose email") },
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
private fun ReplyBar() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = {},
            modifier = Modifier
                .padding(4.dp)
                .size(24.dp)
                .scale(scaleX = -1f, scaleY = 1f)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Shortcut,
                contentDescription = null,
                modifier =
                Modifier
                    .size(24.dp)
            )
        }
        Icon(
            Icons.Default.ArrowDropDown,
            contentDescription = "Dropdown",
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(stringResource(R.string.txt_reply), color = MaterialTheme.colorScheme.onSurface)
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {},
            modifier = Modifier
                .padding(4.dp)
                .size(24.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.Shortcut,
                contentDescription = null,
                modifier =
                Modifier
                    .size(24.dp)
            )
        }
    }
}

@Preview
@Composable
fun EmailDetailsBottomReplyBarPreview (){
    Surface {
        EmailDetailsBottomSection()
    }
}

@Preview
@Composable
fun EmailDetailsBottomWithReplyBarPreview (){
    Surface {
        EmailDetailsBottomSection(isReplyMode = true)
    }
}