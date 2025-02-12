package `in`.iotkiit.raidersreckoningapp.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LinearProgressIndicator
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
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.view.components.core.topbar.TopBar
import `in`.iotkiit.raidersreckoningapp.view.components.myTeam.Fields
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavBar
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavOptions.Companion.bottomNavOptions
import `in`.iotkiit.raidersreckoningapp.vm.DashBoardViewModel
import `in`.iotkiit.raidersreckoningapp.vm.LeaderboardViewModel
import `in`.iotkiit.raidersreckoningapp.vm.TeamViewModel

@Composable
fun MyTeamScreen(
    navController: NavController,
    teamViewModel: TeamViewModel = hiltViewModel(),
    leaderboardViewModel: LeaderboardViewModel = hiltViewModel()
) {

    val teamState = teamViewModel.getTeamState.collectAsState().value
    val leaderboardState = leaderboardViewModel.getLeaderboardData.collectAsState().value

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
                LinearProgressIndicator(color = GreenCOD)
            }
        }

        is UiState.Success -> {
            val data = teamState.data.data
            val teamName = data?.name ?:"NoTeam"
            val points = data?.points ?: 0
            val leaderName = data?.leaderInfo?.name ?: ""
            val participantsList = data?.participantsList ?: emptyList()

            Scaffold(
                containerColor = Color.Black,
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopBar(
                        teamName = teamName,
                        points = points
                    )
                },
                bottomBar = {
                    BottomNavBar(navController = navController, bottomMenu = bottomNavOptions)
                }
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .padding(it)
                        .background(Color.Black),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Fields(
                            field = leaderName,
                            containerColor = GreenCOD,
                            contentColor = Color.Black,
                        )
                        LazyColumn {
                            items(participantsList) { participant ->
                                Fields(participant.name)
                            }
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
                    text = teamState.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}