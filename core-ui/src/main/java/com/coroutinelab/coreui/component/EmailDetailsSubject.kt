package com.coroutinelab.coreui.component


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import me.saket.extendedspans.ExtendedSpans
import me.saket.extendedspans.RoundedCornerSpanPainter
import me.saket.extendedspans.drawBehind

@Composable
fun EmailDetailsSubject(
    modifier: Modifier = Modifier,
    subjectText: String,
    tag: String
) {
    val text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontSize = 21.sp,
                fontWeight = FontWeight.SemiBold
            )
        ) {
            append(subjectText)
        }

        withStyle(
            SpanStyle(
                background = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            append(tag)
        }
    }

    val extendedSpans = remember {
        ExtendedSpans(
            RoundedCornerSpanPainter(
                cornerRadius = 4.sp,
                padding = RoundedCornerSpanPainter.TextPaddingValues(horizontal = 2.sp),
                topMargin = 1.sp,
                bottomMargin = 1.sp,
                stroke = RoundedCornerSpanPainter.Stroke(
                    color = Color(0xFFBF97FF).copy(alpha = 0.6f)
                )
            )
        )
    }
    Text(
        modifier = modifier.drawBehind(extendedSpans),
        text = remember(text) {
            extendedSpans.extend(text)
        },
        onTextLayout = { result ->
            extendedSpans.onTextLayout(result)
        }
    )
}

@Preview
@Composable
fun EmailDetailsSubjectPreview (){
    Surface {
        EmailDetailsSubject(
            subjectText = "Important message ",
            tag = "Inbox"
        )
    }
}