package `in`.iotkiit.raidersreckoningapp.view.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.GetTeamResponse
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD

import `in`.iotkiit.raidersreckoningapp.view.components.myTeam.Fields
import `in`.iotkiit.raidersreckoningapp.vm.TeamViewModel


@Composable
fun ProceedScreen(
    navController: NavHostController,
    teamViewModel: TeamViewModel = hiltViewModel()
) {
    val getTeamState = teamViewModel.getTeamState.collectAsState().value

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
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(8.dp)
            ) {
                Button(
                    onClick = { teamViewModel.getTeam() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Fields("Get Team")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (getTeamState) {
                is UiState.Loading -> {
                    LinearProgressIndicator(color = GreenCOD)
                }
                is UiState.Success -> {
                    val teamData =
                       getTeamState.data.data
                    if (teamData != null) {
                        Text(
                            "Team Name: ${teamData.name}",
                            fontSize = 18.sp
                        )
                    }
                }
                is UiState.Failed -> {
                    Text(
                        text = getTeamState.message,
                        fontSize = 16.sp
                    )
                }
                else -> {}
            }
        }
    }
}