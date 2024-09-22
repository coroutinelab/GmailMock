package com.coroutinelab.coreui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.coroutinelab.coreui.content.getDropDownMenuList

@Composable
fun DetailsAppBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val density = LocalDensity.current
    var isMenuExpanded by remember { mutableStateOf(false) }
    var offsetX by remember { mutableStateOf(0.dp) }
    var parentWidth by remember { mutableIntStateOf(0) }

    Row(
        modifier = modifier.fillMaxWidth().onPlaced {
            parentWidth = it.size.width
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Back")
        }
        Spacer(Modifier.weight(1f))
        IconButton(onClick = { }) {
            Icon(Icons.Default.Archive, contentDescription = "Archive")
        }
        IconButton(onClick = { }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
        IconButton(onClick = { }) {
            Icon(Icons.Default.Mail, contentDescription = "New Mail")
        }
        IconButton(onClick = {  isMenuExpanded = !isMenuExpanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More Options")
        }

        DropdownMenu(
            modifier = Modifier.onPlaced {
                val menuWidth = parentWidth - it.size.width
                offsetX = with(density) { menuWidth.toDp()}
            },
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            offset = DpOffset(offsetX, 0.dp),
        ) {

            getDropDownMenuList().forEach {
                DropdownMenuItem(
                    text = {
                        Text(it.title)
                    },
                    onClick = { it.onClick() },
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DetailsAppBarPreview() {
    val navController = rememberNavController()
    DetailsAppBar(navController = navController)
}


