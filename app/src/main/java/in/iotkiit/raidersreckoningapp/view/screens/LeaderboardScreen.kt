package `in`.iotkiit.raidersreckoningapp.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.view.components.core.topbar.TopBar
import `in`.iotkiit.raidersreckoningapp.view.components.leaderboard.CurrentLeaders
import `in`.iotkiit.raidersreckoningapp.view.components.leaderboard.LeaderboardFields
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavBar
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavOptions.Companion.bottomNavOptions
import `in`.iotkiit.raidersreckoningapp.vm.LeaderboardViewModel
import `in`.iotkiit.raidersreckoningapp.state.UiState
@Composable
fun LeaderboardScreen(
    navController: NavController,
    leaderboardViewModel: LeaderboardViewModel = hiltViewModel()
) {
    val leaderboardDataState = leaderboardViewModel.getLeaderboardData.collectAsState().value

    var teamName by remember { mutableStateOf("No Team") }
    var points by remember { mutableStateOf(0) }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopBar(
                modifier = Modifier.fillMaxWidth(),
                teamName = teamName,
                points = points
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController, bottomMenu = bottomNavOptions)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.Black),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            when (leaderboardDataState) {
                is UiState.Idle -> {
                    leaderboardViewModel.getLeaderboardData
                }

                is UiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = GreenCOD)
                    }
                }

                is UiState.Success -> {
                    val data = leaderboardDataState.data.data

                    if (data != null && data.leaderboard.isNotEmpty()) {
                        val topThreeTeams = data.leaderboard.take(3)
                        val otherTeams = data.leaderboard.drop(3)

                        
                        teamName = (topThreeTeams.firstOrNull()?.team ?: "No Team").toString()
                        points = topThreeTeams.firstOrNull()?.points ?: 0

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "CURRENT LEADERS",
                                style = MaterialTheme.typography.titleSmall,
                                color = Color.White,
                                modifier = Modifier.padding(16.dp)
                            )

                            CurrentLeaders(players = topThreeTeams.map { it.team }.toString())

                            Column(
                                modifier = Modifier.safeDrawingPadding()
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "ROUND",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "30:00",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = Color.White
                                    )
                                }

                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = GreenCOD.copy(1f),
                                            shape = RoundedCornerShape(15.dp)
                                        )
                                        .border(
                                            width = 1.04.dp,
                                            color = GreenCOD,
                                            shape = RoundedCornerShape(12.dp)
                                        )
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = "Rank",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "Team",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "Score",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color.Black
                                    )
                                }

                                Spacer(Modifier.height(1.dp))

                                Column {
                                    otherTeams.forEachIndexed { index, entry ->
                                        LeaderboardFields(
                                            rank = (index + 4).toString(),
                                            teamName = entry.team.toString(),
                                            points = entry.points.toString()
                                        )
                                    }
                                }
                            }
                        }
                    } else {

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No teams available",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }

                is UiState.Failed -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Failed to load leaderboard", color = Color.Red)
                    }
                }

                else -> {}
            }
        }
    }
}
