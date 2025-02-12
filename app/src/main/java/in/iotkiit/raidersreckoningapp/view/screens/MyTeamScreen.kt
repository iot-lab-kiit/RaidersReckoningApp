package `in`.iotkiit.raidersreckoningapp.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.qr_generator_compose.qrGenerator
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.view.components.core.TeamCard
import `in`.iotkiit.raidersreckoningapp.view.components.core.topbar.TopBar
import `in`.iotkiit.raidersreckoningapp.view.components.myTeam.Fields
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavBar
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavOptions.Companion.bottomNavOptions
import `in`.iotkiit.raidersreckoningapp.view.navigation.RaidersReckoningScreens
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
            CircularProgressIndicator()
        }
        is UiState.Success -> {
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(painter = qrGenerator(content = teamState.data.data!!.id,
                        size = 250.dp),
                        contentDescription = null)

                    Spacer(modifier = Modifier.height(16.dp))

                    TeamCard(
                        teamName = teamState.data.data.name,
                        leaderName = teamState.data.data.leaderInfo.name,
                        teamMembers = teamState.data.data.participantsList.map { it.name }
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(8.dp)
                    ) {
                        Button(
                            onClick = { navController.navigate(RaidersReckoningScreens.DashBoardScreen.route) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Fields("Get Team")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        else -> {}
    }
}