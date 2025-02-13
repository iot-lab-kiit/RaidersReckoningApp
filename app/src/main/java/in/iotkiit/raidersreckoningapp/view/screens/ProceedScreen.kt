package `in`.iotkiit.raidersreckoningapp.view.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.qr_generator_compose.qrGenerator
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.view.components.anims.FailureAnimationDialog
import `in`.iotkiit.raidersreckoningapp.view.components.core.PrimaryButton
import `in`.iotkiit.raidersreckoningapp.view.components.core.TeamCard
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