package `in`.iotkiit.raidersreckoningapp.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.ui.theme.modernWarfare
import `in`.iotkiit.raidersreckoningapp.view.components.anims.FailureAnimationDialog
import `in`.iotkiit.raidersreckoningapp.view.components.anims.LoadingTransition
import `in`.iotkiit.raidersreckoningapp.view.components.core.topbar.TopBar
import `in`.iotkiit.raidersreckoningapp.view.components.core.useGlobalTimer
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavBar
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavOptions.Companion.bottomNavOptions
import `in`.iotkiit.raidersreckoningapp.view.navigation.RaidersReckoningScreens
import `in`.iotkiit.raidersreckoningapp.vm.DashBoardViewModel
import kotlinx.coroutines.delay

@Composable
fun DashBoardScreen(
    navController: NavController,
    dashBoardViewModel: DashBoardViewModel = hiltViewModel()
) {
    val dashBoardState = dashBoardViewModel.getDashBoardState.collectAsState().value
    var remainingTime by remember { mutableLongStateOf(0L) }

    val isRefreshing = dashBoardViewModel.isRefreshing.collectAsState().value

    when (dashBoardState) {
        is UiState.Idle -> {
            dashBoardViewModel.getDashBoardData()
        }

        is UiState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoadingTransition()
            }
        }

        is UiState.Success -> {

            val data = dashBoardState.data.data
            val teamName = data?.teamName ?: "NoTeam"
            val points = data?.points ?: 0
            val zoneName = data?.zone?.name ?: "No zone assigned"
            val zoneVenue = data?.zone?.venue ?: ""
            val round = data?.round ?: 0
            val startTime = data?.zone?.startTime ?: 0L
            val duration = data?.zone?.duration ?: 0L
            val challenger = data?.isChallenger ?: true
            //logic for timer
            LaunchedEffect(data?.zone?.startTime) {
                while (remainingTime > 0) {
                    val currentTime = System.currentTimeMillis()
                    val endTime = startTime + duration
                    remainingTime = (endTime - currentTime).coerceAtLeast(0)
                    delay(1000) // Update every second
                }
            }

            Scaffold(
                containerColor = Color.Black,
                bottomBar = {
                    BottomNavBar(navController = navController, bottomMenu = bottomNavOptions)
                },
                topBar = {
                    TopBar(
                        teamName = teamName,
                        points = points
                    )
                }
            ) {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing),
                    onRefresh = { dashBoardViewModel.refreshDashboardData() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

                        val image = when (zoneName) {
                            "CRASH" -> R.drawable.crash
                            "HIGHRISE" -> R.drawable.highrise
                            "HIJACKED" -> R.drawable.hijacked
                            "NUKETOWN" -> R.drawable.nuketown
                            "RUST" -> R.drawable.rust
                            "SHIPMENT" -> R.drawable.shipment
                            "TERMINAL" -> R.drawable.terminal
                            else -> R.drawable.gulag
                        }

                        item {
                            Image(
                                painter = painterResource(image),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp, end = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = zoneName,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = Color.White
                                )
                                Text(
                                    text = zoneVenue,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = GreenCOD
                                )

                            }
                            Spacer(Modifier.height(20.dp))
                        }
                        if (dashBoardState.data.data!!.paused) {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Currently on Break!",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = GreenCOD
                                    )
                                }
                            }
                        } else {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Round $round",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = GreenCOD
                                    )

                                    val (minutes, seconds, _) = useGlobalTimer(
                                        zoneStartTime = dashBoardState.data.data?.zone?.startTime,
                                        zoneDuration = dashBoardState.data.data?.zone?.duration
                                            ?: 0L
                                    )

                                    Text(
                                        text = String.format("%02d:%02d", minutes, seconds),
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontFamily = modernWarfare
                                    )
                                }
                            }
                        }
                        item {
                            if (challenger) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "You are a challenger!",
                                    textAlign = TextAlign.Center,
                                    maxLines = 2,
                                    color = Color.Yellow,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            } else {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "You are a capturer!",
                                    textAlign = TextAlign.Center,
                                    maxLines = 2,
                                    color = Color.Red,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                            }
                        }
                        item {
                            FloatingActionButton(
                                onClick = {
                                    navController.navigate(RaidersReckoningScreens.GetQuestionsQR.route)
                                },
                                containerColor = GreenCOD,
                                contentColor = Color.Black,
                            ) {
                                Image(
                                    modifier = Modifier.padding(6.dp),
                                    painter = painterResource(R.drawable.scan),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }

            }
        }

        is UiState.Failed -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                FailureAnimationDialog(
                    message = dashBoardState.message,
                    onTryAgainClick = { dashBoardViewModel.getDashBoardData() }
                )
            }
        }
    }
}