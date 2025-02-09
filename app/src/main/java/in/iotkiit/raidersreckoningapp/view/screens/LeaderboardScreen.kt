package `in`.iotkiit.raidersreckoningapp.view.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LeaderboardScreen(
    navController: NavController,
    leaderboardViewModel: LeaderboardViewModel = hiltViewModel()
) {

    val leaderboardDataState = leaderboardViewModel.getLeaderboardData.collectAsState().value

    Scaffold(
        topBar = {
            TopBar(
                modifier = Modifier.fillMaxWidth(),
                teamName = "TaskForce141",
                points = 10
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController, bottomMenu = bottomNavOptions)
        }
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
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
                            modifier = Modifier.padding(vertical = 16.dp)

                        )

                        CurrentLeaders(
                            players = listOf(
                                "imissher",
                                "noobmaster69",
                                "zrfghrrh"
                            )
                        )
                    }

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
                        Spacer(Modifier.height(1.dp))
                        LeaderboardFields("TasForce141", "1", "69")
                        Spacer(Modifier.height(1.dp))
                        LeaderboardFields("GuerillaForce", "2", "42")
                        Spacer(Modifier.height(1.dp))
                        LeaderboardFields("Shakalaka", "3", "1")
                    }
                }
            }
        }

//        when (leaderboardDataState) {
//            is UiState.Idle -> {
//                leaderboardViewModel.getLeaderboardData("")
//            }
//
//            is UiState.Loading -> {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    LinearProgressIndicator(color = GreenCOD)
//                }
//            }
//
//            is UiState.Success -> {
//                Surface(
//                    modifier = Modifier.fillMaxSize().safeContentPadding()
//                ) {
//                    Fields("Kunal Saha", 0.1f)
//                }
//            }
//
//            is UiState.Failed -> {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(text = "FAILED!")
//                }
//            }
//        }


    }

}