package com.coroutinelab.coreui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Shortcut
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.coroutinelab.coreui.R
import com.coroutinelab.coreui.theme.Dimensions

@Composable
fun EmailDetailsSenderInfo(
    modifier: Modifier = Modifier,
    profileImageUrl: String,
    isPromotional: Boolean,
    from: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularProfileImage(
            modifier = Modifier,
            imageSource = profileImageUrl,
            size = Dimensions.dimen_32
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(Dimensions.dimen_8)
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    modifier = Modifier.weight(4f),
                    text = from,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "7 days ago",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
            }
            Text(
                text = stringResource(R.string.txt_to_me),
                fontSize = 11.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 1
            )
        }

        if (!isPromotional) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(Dimensions.dimen_4)
                    .size(Dimensions.dimen_32)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Mood,
                    contentDescription = "React with emoji",
                    modifier =
                    Modifier
                        .size(Dimensions.dimen_24)
                )
            }
            IconButton(
                onClick = {},
                modifier = Modifier
                    .padding(Dimensions.dimen_4)
                    .size(Dimensions.dimen_32)
                    .scale(scaleX = -1f, scaleY = 1f)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Shortcut,
                    contentDescription = null,
                    modifier = Modifier.size(Dimensions.dimen_24)
                )
            }
        } else {
            TextButton(
                onClick = { }
            ) {
                Text(stringResource(R.string.txt_unsubscribe))
            }
        }
        IconButton(
            onClick = {},
            modifier = Modifier.size(Dimensions.dimen_32)
        ) {
            Icon(
                imageVector = Icons.Outlined.MoreVert,
                contentDescription = null,
                modifier = Modifier.size(Dimensions.dimen_24)
            )
        }
    }
}

@Preview
@Composable
fun EmailDetailsSenderInfoPreview() {
    Surface {
        EmailDetailsSenderInfo(
            modifier = Modifier,
            profileImageUrl = "https://i.pravatar.cc/250?img=5",
            isPromotional = true,
            from = "CoroutineLab"
        )
    }
}

@Preview
@Composable
fun NonPromotionalEmailDetailsSenderInfoPreview() {
    Surface {
        EmailDetailsSenderInfo(
            profileImageUrl = "https://i.pravatar.cc/250?img=5",
            isPromotional = false,
            from = "CoroutineLab"
        )
    }
}
