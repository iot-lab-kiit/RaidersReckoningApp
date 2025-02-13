package `in`.iotkiit.raidersreckoningapp.view.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Text
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.data.model.CreateTeamBody
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.view.components.core.CustomOutlinedTextField
import `in`.iotkiit.raidersreckoningapp.view.components.core.PrimaryButton
import `in`.iotkiit.raidersreckoningapp.view.navigation.RaidersReckoningScreens
import `in`.iotkiit.raidersreckoningapp.vm.TeamViewModel
import kotlinx.coroutines.launch

@Composable
fun CreateTeamScreen(
    navController: NavHostController,
    teamViewModel: TeamViewModel = hiltViewModel()
) {
    val teamName = remember { mutableStateOf("") }
    val createTeamState = teamViewModel.createTeamState.collectAsState().value
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(createTeamState) {
        when (createTeamState) {
            is UiState.Success -> {
                Toast.makeText(context, "Team created", Toast.LENGTH_SHORT).show()
                navController.navigate(RaidersReckoningScreens.ProceedScreen.route) {
                    popUpTo(RaidersReckoningScreens.CreateTeamScreen.route) {
                        saveState = true
                        inclusive = true
                    }
                }
            }

            is UiState.Failed -> {
                Toast.makeText(context, createTeamState.message, Toast.LENGTH_SHORT).show()
            }

            else -> {}
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(R.drawable.background),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = painterResource(R.drawable.title),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth(0.75f)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CustomOutlinedTextField(
                        value = teamName.value,
                        onValueChange = { teamName.value = it },
                        placeholder = "TEAM NAME",
                        fontSize = 24,
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.height(16.dp))

                    PrimaryButton(
                        onClick = {
                            if (teamName.value.isNotEmpty()) {
                                coroutineScope.launch {
                                    teamViewModel.createTeam(CreateTeamBody(teamName.value))
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please enter a team name",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        text = "SUBMIT"
                    )
                    if (createTeamState is UiState.Loading) {
                        Text(
                            text = "Creating Team...",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
