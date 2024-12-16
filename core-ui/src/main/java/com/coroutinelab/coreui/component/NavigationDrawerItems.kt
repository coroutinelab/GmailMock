package com.coroutinelab.coreui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.coroutinelab.coreui.theme.Dimensions

@Composable
fun DrawerTitleItem(title: String) {
    Text(
        modifier = Modifier.padding(Dimensions.dimen_8),
        text = title,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        style = MaterialTheme.typography.titleSmall
    )
}

@Composable
fun DrawerMenuItem(title: String, icon: ImageVector, onCLick: () -> Unit) {
    NavigationDrawerItem(
        label = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleSmall
            )
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.size(Dimensions.dimen_24)
            )
        },
        onClick = { onCLick() },
        selected = false
    )
}

@Composable
fun DrawerDivider() {
    HorizontalDivider(
        modifier = Modifier.fillMaxWidth().padding(Dimensions.dimen_8),
        color = MaterialTheme.colorScheme.outline
    )
}

@Composable
fun DrawerCategory(title: String) {
    Text(
        text = title,
        modifier = Modifier.padding(Dimensions.dimen_8),
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        style = MaterialTheme.typography.labelSmall
    )
}
