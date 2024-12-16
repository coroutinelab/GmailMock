package com.coroutinelab.gmailmock

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import com.coroutinelab.coreui.theme.Dimensions
import com.coroutinelab.coreui.theme.GmailMockTheme
import com.coroutinelab.coreui.uimodel.DrawerData
import com.coroutinelab.gmailmock.navigation.EmailDetails
import com.coroutinelab.gmailmock.navigation.EmailList
import com.coroutinelab.presentation.emaildetails.EmailDetailsScreen
import com.coroutinelab.presentation.emaildetails.mvi.EmailDetailsViewModel
import com.coroutinelab.presentation.emaillist.EmailListScreen
import com.coroutinelab.presentation.emaillist.mvi.EmailListContract
import com.coroutinelab.presentation.emaillist.mvi.EmailListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
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
                            DrawerContent(drawerState, scope)
                        },
                        scrimColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.4f),
                        content = {
                            Scaffold(
                                floatingActionButton = {
                                    ExtendedFloatingActionButton(
                                        text = { Text(stringResource(R.string.txt_compose)) },
                                        icon = {
                                            Icon(
                                                Icons.Filled.Edit,
                                                contentDescription = stringResource(R.string.accessibility_compose)
                                            )
                                        },
                                        onClick = {
                                            scope.launch {
                                                drawerState.apply {
                                                    if (isClosed) open() else close()
                                                }
                                            }
                                        }
                                    )
                                },
                                topBar = {
                                    when (topAppbarState) {
                                        TopAppbarState.HOME -> HomeAppBar(
                                            modifier = Modifier.padding(
                                                start = Dimensions.dimen_16,
                                                end = Dimensions.dimen_16,
                                                top = Dimensions.dimen_16
                                            )
                                        ) {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }

                                        TopAppbarState.DETAILS -> DetailsAppBar(
                                            modifier = Modifier.padding(
                                                start = Dimensions.dimen_16,
                                                end = Dimensions.dimen_16,
                                                top = Dimensions.dimen_16
                                            ),
                                            navController = navController
                                        )
                                    }
                                },
                                bottomBar = {
                                    BottomAppBar(modifier = Modifier.semantics { contentDescription = "BottomBar" }) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            IconButton(onClick = {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    "Email Clicked",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }) {
                                                Icon(
                                                    imageVector = Icons.Filled.Email,
                                                    contentDescription = stringResource(R.string.accessibility_email)
                                                )
                                            }
                                            IconButton(onClick = {
                                                Toast.makeText(
                                                    this@MainActivity,
                                                    "Video Call Clicked",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }) {
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
                                                tween(ANIMATION_DURATION)
                                            )
                                        },
                                        popEnterTransition = {
                                            return@composable slideIntoContainer(
                                                AnimatedContentTransitionScope.SlideDirection.End,
                                                tween(ANIMATION_DURATION)
                                            )
                                        }
                                    ) {
                                        topAppbarState = TopAppbarState.HOME
                                        val viewModel: EmailListViewModel = hiltViewModel()
                                        val state by viewModel.state.collectAsStateWithLifecycle()
                                        val dispatch: (EmailListContract.EmailListEvent) -> Unit = { event ->
                                            viewModel.event(event)
                                        }

                                        LaunchedEffect(key1 = Unit) {
                                            viewModel.effect.collect { it ->
                                                when (it) {
                                                    is EmailListContract.EmailListEffect.NavigateToEmailDetails -> with(
                                                        it
                                                    ) {
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
                                            }
                                        }

                                        EmailListScreen(
                                            state = state,
                                            dispatch = dispatch
                                        )
                                    }

                                    composable<EmailDetails>(
                                        enterTransition = {
                                            return@composable slideIntoContainer(
                                                AnimatedContentTransitionScope.SlideDirection.Start,
                                                tween(ANIMATION_DURATION)
                                            )
                                        },
                                        popExitTransition = {
                                            return@composable slideOutOfContainer(
                                                AnimatedContentTransitionScope.SlideDirection.End,
                                                tween(ANIMATION_DURATION)
                                            )
                                        }
                                    ) {
                                        topAppbarState = TopAppbarState.DETAILS
                                        val args = it.toRoute<EmailDetails>()
                                        val viewModel: EmailDetailsViewModel = hiltViewModel()
                                        val state by viewModel.state.collectAsStateWithLifecycle()

                                        EmailDetailsScreen(
                                            state = state,
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

    private companion object {
        const val ANIMATION_DURATION = 700
    }
}

@Composable
fun DrawerContent(drawerState: DrawerState, scope: CoroutineScope) {
    val drawerItemList: List<DrawerData> = getDrawerItemsList()
    ModalDrawerSheet {
        drawerItemList.forEach {
            when (it) {
                is DrawerData.Category -> DrawerCategory(it.title)
                DrawerData.Divider -> DrawerDivider()
                is DrawerData.Item -> DrawerMenuItem(it.title, it.icon) {
                    scope.launch {
                        drawerState.close()
                    }
                }
                is DrawerData.Title -> DrawerTitleItem(it.title)
            }
        }
    }
}
