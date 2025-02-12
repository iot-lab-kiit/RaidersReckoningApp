package `in`.iotkiit.raidersreckoningapp.view.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.qr_generator_compose.qrGenerator
import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.GetTeamResponse
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.view.components.core.PrimaryButton
import `in`.iotkiit.raidersreckoningapp.view.components.core.TeamCard

import `in`.iotkiit.raidersreckoningapp.view.components.myTeam.Fields
import `in`.iotkiit.raidersreckoningapp.view.navigation.RaidersReckoningScreens
import `in`.iotkiit.raidersreckoningapp.vm.TeamViewModel


@Composable
fun ProceedScreen(
    navController: NavHostController,
    teamViewModel: TeamViewModel = hiltViewModel()
) {

    val teamState = teamViewModel.getTeamState.collectAsState().value

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
            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {

                    Image(
                        painter = qrGenerator(
                            content = teamState.data.data!!.id,
                            size = 250.dp
                        ),
                        contentDescription = null
                    )

                    TeamCard(
                        teamName = teamState.data.data.name,
                        leaderName = teamState.data.data.leaderInfo.name,
                        teamMembers = teamState.data.data.participantsList.map { it.name }
                    )


                    PrimaryButton(
                        onClick = { navController.navigate(RaidersReckoningScreens.DashBoardScreen.route) },
                        text = "Proceed",
                        contentColor = GreenCOD,
                        containerColor = GreenCOD.copy(0.1f)
                    )
                }
            }
        }
        else -> {}
    }
}