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
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
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
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.view.components.myTeam.Fields
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavBar
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavOptions.Companion.bottomNavOptions
import `in`.iotkiit.raidersreckoningapp.vm.DashBoardViewModel
import `in`.iotkiit.raidersreckoningapp.vm.TeamViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyTeamScreen(
    navController: NavController,
    dashBoardViewModel: DashBoardViewModel = hiltViewModel()
) {

    val getDashBoardState = dashBoardViewModel.getDashBoardState.collectAsState().value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavBar(navController = navController, bottomMenu = bottomNavOptions)
        }
    ) {
        Surface(
            modifier = Modifier.safeContentPadding().fillMaxSize()
        ) {
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .background(
                            color = GreenCOD.copy(1f),
                            shape = RoundedCornerShape(15.dp)
                        )
                        .border(
                            width = 1.04.dp,
                            color = GreenCOD,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(20.dp)
                ) {
                    Text(
                        text = "TaskForce141",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.Black
                    )
                }
                Spacer(Modifier.height(2.dp))
                Fields("Kunal", 0.1f)
                Spacer(Modifier.height(2.dp))
                Fields("Binayak", 0.1f)
                Spacer(Modifier.height(2.dp))
                Fields("Sarthak", 0.1f)
            }
        }

//        when (getDashBoardState) {
//            is UiState.Idle -> {
//                dashBoardViewModel.getDashBoardData("")
//            }
//
//            is UiState.Loading -> {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    verticalArrangement = Arrangement.Center,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    LinearProgressIndicator()
//                }
//            }
//
//            is UiState.Success -> {
//                Surface(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .safeContentPadding()
//                ) {
//                    Fields("Kunal")
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