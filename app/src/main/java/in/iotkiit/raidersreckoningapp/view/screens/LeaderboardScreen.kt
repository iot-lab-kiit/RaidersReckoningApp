package `in`.iotkiit.raidersreckoningapp.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.view.components.anims.FailureAnimationDialog
import `in`.iotkiit.raidersreckoningapp.view.components.anims.LoadingTransition
import `in`.iotkiit.raidersreckoningapp.view.components.core.topbar.TopBar
import `in`.iotkiit.raidersreckoningapp.view.components.leaderboard.CurrentLeaders
import `in`.iotkiit.raidersreckoningapp.view.components.leaderboard.LeaderboardFields
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavBar
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavOptions.Companion.bottomNavOptions
import `in`.iotkiit.raidersreckoningapp.vm.LeaderboardViewModel

@Composable
fun LeaderboardScreen(
    navController: NavController,
    leaderboardViewModel: LeaderboardViewModel = hiltViewModel()
) {
    val leaderboardDataState = leaderboardViewModel.getLeaderboardData.collectAsState().value
    val isRefreshing = leaderboardViewModel.isRefreshing.collectAsState().value

    when (leaderboardDataState) {
        is UiState.Idle -> {
            leaderboardViewModel.getLeaderboardData()
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
            Scaffold(
                containerColor = Color.Black,
                topBar = {
                    TopBar(
                        modifier = Modifier.fillMaxWidth(),
                        teamName = leaderboardDataState.data.data!!.teamName,
                        points = leaderboardDataState.data.data.points
                    )
                },
                bottomBar = {
                    BottomNavBar(navController = navController, bottomMenu = bottomNavOptions)
                }
            ) { paddingValues ->
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing),
                    onRefresh = { leaderboardViewModel.refreshLeaderboardData() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black),
                        verticalArrangement = Arrangement.Top
                    ) {
                        item {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Column {
                                    Text(
                                        text = "CURRENT LEADERS",
                                        style = MaterialTheme.typography.titleSmall,
                                        color = Color.White,
                                        modifier = Modifier.padding(16.dp)
                                    )
                                    CurrentLeaders(
                                        players = leaderboardDataState.data.data?.leaderboard?.take(3)
                                            ?.map { teams -> teams.team.name } ?: emptyList()
                                    )
                                }

                                Column(
                                    modifier = Modifier.safeDrawingPadding()
                                ) {

                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp)
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
                                            .padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "rank",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = "Team",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = "score",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                        }

                        itemsIndexed(
                            leaderboardDataState.data.data?.leaderboard ?: emptyList()
                        ) { index, team ->
                            val name = if (team.team.name.length >= 16) team.team.name.take(14) + "..." else team.team.name
                            LeaderboardFields(
                                name,
                                (index + 1).toString(),
                                team.team.points.toString()
                            )
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
                    message = leaderboardDataState.message,
                    onTryAgainClick = { leaderboardViewModel.getLeaderboardData() }
                )
            }
        }
    }
}