package com.coroutinelab.coreui.content

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.LabelImportant
import androidx.compose.material.icons.automirrored.filled.ScheduleSend
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Drafts
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LabelImportant
import androidx.compose.material.icons.filled.Outbox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Snooze
import androidx.compose.material.icons.filled.Star
import com.coroutinelab.coreui.uimodel.DrawerData

fun getDrawerItemsList() = listOf(
    DrawerData.Title("Gmail"),
    DrawerData.Divider,
    DrawerData.Item("All Inbox", Icons.Filled.AllInbox),
    DrawerData.Divider,
    DrawerData.Item("Primary", Icons.Filled.Inbox),
    DrawerData.Item("Update", Icons.Filled.Info),
    DrawerData.Item("Forum", Icons.Filled.Forum),
    DrawerData.Category("All Label"),
    DrawerData.Item("Starred", Icons.Filled.Star),
    DrawerData.Item("Snoozed", Icons.Filled.Snooze),
    DrawerData.Item("Important", Icons.Filled.LabelImportant),
    DrawerData.Item("Send", Icons.AutoMirrored.Filled.LabelImportant),
    DrawerData.Item("Schedule", Icons.AutoMirrored.Filled.ScheduleSend),
    DrawerData.Item("Outbox", Icons.Filled.Outbox),
    DrawerData.Item("Draft", Icons.Filled.Drafts),
    DrawerData.Item("All Email", Icons.Filled.Email),
    DrawerData.Item("Delete", Icons.Filled.DeleteOutline),
    DrawerData.Category("Google Apps"),
    DrawerData.Item("Calendar", Icons.Filled.CalendarMonth),
    DrawerData.Item("Contacts", Icons.Filled.Contacts),
    DrawerData.Divider,
    DrawerData.Item("Settings", Icons.Filled.Settings),
    DrawerData.Item("Help", Icons.Filled.Help),
)