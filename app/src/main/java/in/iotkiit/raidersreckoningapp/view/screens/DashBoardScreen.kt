package `in`.iotkiit.raidersreckoningapp.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.view.components.core.topbar.TopBar
import `in`.iotkiit.raidersreckoningapp.view.components.dashboard.MapCard
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavBar
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavOptions.Companion.bottomNavOptions
import `in`.iotkiit.raidersreckoningapp.vm.DashBoardViewModel
import kotlinx.coroutines.delay

@Composable
fun DashBoardScreen(
    navController: NavController,
    dashBoardViewModel: DashBoardViewModel = hiltViewModel()
) {
    val dashBoardState = dashBoardViewModel.getDashBoardState.collectAsState().value
    var remainingTime by remember { mutableLongStateOf(0L) }

    when(dashBoardState) {
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
                LinearProgressIndicator(color = GreenCOD)
            }
        }

        is UiState.Success -> {

            val data = dashBoardState.data.data
            val teamName = data?.teamName?:"NoTeam"
            val points = data?.points ?: 0
            val zoneName = data?.zone?.name?:"No zone"
            val zoneVenue = data?.zone?.venue?:"assigned"
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
                Column(
                    modifier = Modifier
                        .padding(it)
                        .padding(16.dp)
                        .fillMaxSize()
                        .background(Color.Black),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
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

                        val minutes = (remainingTime / 1000 / 60).toInt()
                        val seconds = ((remainingTime / 1000) % 60).toInt()

                        Text(
                            text = String.format("%02d:%02d", minutes, seconds),
                            color = when {
                                remainingTime <= 300000 -> Color.Red  // Less than 5 minutes
                                remainingTime <= 600000 -> Color.Yellow // Less than 10 minutes
                                else -> Color.White
                            },
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    MapCard(
                        mapImage = painterResource(R.drawable.map),
                        soldierImage = painterResource(R.drawable.soldier)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = zoneName,
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.White
                        )
                        Text(
                            modifier = Modifier.padding(start = 5.dp, bottom = 5.dp),
                            text = zoneVenue,
                            style = MaterialTheme.typography.labelSmall,
                            color = GreenCOD
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(0.75f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (challenger){
                            Text(
                                text = "You are a challenger!",
                                textAlign = TextAlign.Center,
                                color = Color.Yellow,
                                style = MaterialTheme.typography.displaySmall
                            )
                        } else {
                            Text(
                                text = "You are not a challenger",
                                textAlign = TextAlign.Center,
                                color = Color.Red,
                                style = MaterialTheme.typography.displaySmall
                            )
                        }

                    }

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        FloatingActionButton(
                            onClick = { /*TODO*/ },
                            containerColor = GreenCOD,
                            contentColor = Color.Black,
                        ) {
                            Image(
                                modifier = Modifier.padding(8.dp),
                                painter = painterResource(R.drawable.scan),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }

        is UiState.Failed -> {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = dashBoardState.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}