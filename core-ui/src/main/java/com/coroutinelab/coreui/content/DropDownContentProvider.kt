package com.coroutinelab.coreui.content

import com.coroutinelab.coreui.uimodel.DropDownMenuItem

fun getDropDownMenuList() = listOf<DropDownMenuItem>(
    DropDownMenuItem(title = "Move to"){},
    DropDownMenuItem(title = "Snooze"){},
    DropDownMenuItem(title = "Change Labels"){},
    DropDownMenuItem(title = "Mark Important"){},
    DropDownMenuItem(title = "Mute"){},
    DropDownMenuItem(title = "View in light theme"){},
    DropDownMenuItem(title = "Print"){},
    DropDownMenuItem(title = "Report Spam"){},
    DropDownMenuItem(title = "Add to tasks"){},
    DropDownMenuItem(title = "Help & feedback"){}
)