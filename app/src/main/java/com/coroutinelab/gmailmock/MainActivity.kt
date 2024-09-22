package com.coroutinelab.gmailmock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.coroutinelab.coreui.component.DetailsAppBar
import com.coroutinelab.coreui.component.DrawerCategory
import com.coroutinelab.coreui.component.DrawerDivider
import com.coroutinelab.coreui.component.DrawerMenuItem
import com.coroutinelab.coreui.component.DrawerTitleItem
import com.coroutinelab.coreui.component.HomeAppBar
import com.coroutinelab.coreui.content.getDrawerItemsList
import com.coroutinelab.coreui.theme.GmailMockTheme
import com.coroutinelab.coreui.uimodel.DrawerData
import com.coroutinelab.gmailmock.navigation.EmailDetails
import com.coroutinelab.gmailmock.navigation.EmailList
import com.coroutinelab.presentation.emaildetails.EmailDetailsScreen
import com.coroutinelab.presentation.emaillist.EmailListScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(Modifier.safeDrawingPadding()) {
                GmailMockTheme {
                    val navController = rememberNavController()
                    var topAppbarState by remember { mutableStateOf(TopAppbarState.HOME) }
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            DrawerContent()
                        },
                        scrimColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.4f),
                        content = {
                            Scaffold(
                                topBar = {
                                    when (topAppbarState) {
                                        TopAppbarState.HOME -> HomeAppBar(
                                            modifier = Modifier.padding(
                                                start = 16.dp,
                                                end = 16.dp,
                                                top = 16.dp
                                            )
                                        ) {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }

                                        TopAppbarState.DETAILS -> DetailsAppBar(
                                            modifier = Modifier.padding(
                                                start = 16.dp,
                                                end = 16.dp,
                                                top = 16.dp
                                            ), navController = navController
                                        )
                                    }
                                },
                                bottomBar = {
                                    BottomAppBar(modifier = Modifier) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            IconButton(onClick = { }) {
                                                Icon(
                                                    imageVector = Icons.Filled.Email,
                                                    contentDescription = "Email"
                                                )
                                            }
                                            IconButton(onClick = { }) {
                                                Icon(
                                                    imageVector = Icons.Filled.Videocam,
                                                    contentDescription = "Video Call"
                                                )
                                            }
                                        }

                                    }
                                }
                            ) { innerPadding ->
                                NavHost(
                                    navController = navController,
                                    startDestination = EmailList,
                                    modifier = Modifier.padding(innerPadding)
                                ) {
                                    composable<EmailList>(
                                        exitTransition = {
                                            return@composable slideOutOfContainer(
                                                AnimatedContentTransitionScope.SlideDirection.Start,
                                                tween(700)
                                            )
                                        },
                                        popEnterTransition = {
                                            return@composable slideIntoContainer(
                                                AnimatedContentTransitionScope.SlideDirection.End,
                                                tween(700)
                                            )
                                        }
                                    ) {
                                        topAppbarState = TopAppbarState.HOME
                                        EmailListScreen { model ->
                                            navController.navigate(
                                                EmailDetails(
                                                    from = model.from,
                                                    profileImage = model.profileImage,
                                                    subject = model.subject,
                                                    isPromotional = model.isPromotional,
                                                    isStarred = model.isStarred
                                                )
                                            )
                                        }
                                    }

                                    composable<EmailDetails>(
                                        enterTransition = {
                                            return@composable slideIntoContainer(
                                                AnimatedContentTransitionScope.SlideDirection.Start,
                                                tween(700)
                                            )
                                        },
                                        popExitTransition = {
                                            return@composable slideOutOfContainer(
                                                AnimatedContentTransitionScope.SlideDirection.End,
                                                tween(700)
                                            )
                                        }
                                    ) {
                                        topAppbarState = TopAppbarState.DETAILS
                                        val args = it.toRoute<EmailDetails>()
                                        EmailDetailsScreen(
                                            from = args.from,
                                            profileImage = args.profileImage,
                                            subject = args.subject,
                                            isPromotional = args.isPromotional
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DrawerContent() {
    val drawerItemList:List<DrawerData> = getDrawerItemsList()
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.8f)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(
                drawerItemList
            ) {
                when(it){
                    is DrawerData.Category -> DrawerCategory(it.title)
                    DrawerData.Divider -> DrawerDivider()
                    is DrawerData.Item ->  DrawerMenuItem(it.title, it.icon) { }
                    is DrawerData.Title ->  DrawerTitleItem(it.title)
                }

            }
        }
    }
}




