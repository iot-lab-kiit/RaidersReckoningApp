package `in`.iotkiit.raidersreckoningapp.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.google.firebase.auth.FirebaseAuth
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.view.components.anims.FailureAnimationDialog
import `in`.iotkiit.raidersreckoningapp.view.components.anims.LoadingTransition
import `in`.iotkiit.raidersreckoningapp.view.components.core.PrimaryButton
import `in`.iotkiit.raidersreckoningapp.view.components.core.TeamCard
import `in`.iotkiit.raidersreckoningapp.view.components.core.topbar.TopBar
import `in`.iotkiit.raidersreckoningapp.view.components.leaderboard.LeaderboardFields
import `in`.iotkiit.raidersreckoningapp.view.components.myTeam.ExpandableQRCard
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavBar
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavOptions.Companion.bottomNavOptions
import `in`.iotkiit.raidersreckoningapp.view.navigation.RaidersReckoningScreens
import `in`.iotkiit.raidersreckoningapp.vm.LeaderboardViewModel
import `in`.iotkiit.raidersreckoningapp.vm.TeamViewModel


@Composable
fun MyTeamScreen(
    navController: NavController,
    teamViewModel: TeamViewModel = hiltViewModel()
) {
    val teamState = teamViewModel.getTeamState.collectAsState().value

    val isRefreshing = teamViewModel.isRefreshing.collectAsState().value

    when (teamState) {
        is UiState.Idle -> {
            teamViewModel.getTeam()
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
            val data = teamState.data.data
            val teamName = data?.name ?: "NoTeam"
            val points = data?.points ?: 0
            val totalPoints = data?.points ?: 0

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
            ) { paddingValues ->
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing),
                    onRefresh = { teamViewModel.refreshTeamData() },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        item {
                            ExpandableQRCard(teamId = teamState.data.data!!.id)
                        }
                        item {
                            TeamCard(
                                teamName = teamState.data.data!!.name,
                                leaderName = teamState.data.data.leaderInfo.name,
                                teamMembers = teamState.data.data.participantsList.map { it.name }
                            )
                        }

                        item {

                            if (teamState.data.data!!.statsList.isEmpty()) {
                                Text(
                                    text = "No rounds played yet",
                                    color = Color.White,
                                    modifier = Modifier.padding(16.dp),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            } else {
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
                                        text = "ROUND",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "POINTS",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "WINNER",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = Color.Black
                                    )
                                }

                                teamState.data.data.statsList.forEach { stats ->
                                    LeaderboardFields(
                                        teamName = stats.points,
                                        points = stats.winner,
                                        rank = stats.round
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }

                        item {
                            PrimaryButton(
                                onClick = {
                                    FirebaseAuth.getInstance().signOut()
                                    navController.navigate(RaidersReckoningScreens.LoginScreen.route) {
                                        popUpTo(RaidersReckoningScreens.MyTeamScreen.route) {
                                            inclusive = true
                                        }
                                    }
                                },
                                text = "Log Out",
                                contentColor = GreenCOD,
                                containerColor = GreenCOD.copy(0.1f)
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
                    message = teamState.message,
                    onTryAgainClick = { teamViewModel.getTeam() }
                )
            }
        }
    }
}

