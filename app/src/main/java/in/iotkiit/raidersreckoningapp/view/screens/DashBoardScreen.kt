package `in`.iotkiit.raidersreckoningapp.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.view.components.core.topbar.TopBar
import `in`.iotkiit.raidersreckoningapp.view.components.dashboard.MapCard
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavBar
import `in`.iotkiit.raidersreckoningapp.view.navigation.BottomNavOptions.Companion.bottomNavOptions
import `in`.iotkiit.raidersreckoningapp.vm.DashBoardViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashBoardScreen(
    navController: NavController,
    viewModel: DashBoardViewModel = hiltViewModel()
) {

    val getDashBoardState = viewModel.getDashBoardState.collectAsState().value

    Scaffold(
        modifier = Modifier,
        bottomBar = {
            BottomNavBar(navController = navController, bottomMenu = bottomNavOptions)
        },
        topBar = {
            TopBar(
                modifier = Modifier.fillMaxWidth(),
                teamName = "TaskForce141",
                points = 10
            )
        }
    ) {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Spacer(modifier = Modifier.height(96.dp))

                MapCard(
                    mapImage = painterResource(R.drawable.map),
                    soldierImage = painterResource(R.drawable.soldier)
                )

                Column {

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "NUKETOWN",
                            style = MaterialTheme.typography.displayMedium,
                            color = Color.White
                        )

                        Text(
                            text = "C001",
                            style = MaterialTheme.typography.displayMedium,
                            color = GreenCOD
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Round 1",
                            style = MaterialTheme.typography.displayMedium,
                            color = GreenCOD
                        )

                        Text(
                            modifier = Modifier.padding(16.dp),
                            color = Color.White,
                            text = "30:00",
                            style = MaterialTheme.typography.displayMedium
                        )
                    }

                    FloatingActionButton(
                        onClick = { /*TODO*/ },
                        containerColor = GreenCOD,
                        contentColor = Color.Black,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Image(
                            modifier = Modifier.padding(16.dp),
                            painter = painterResource(R.drawable.scan),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }

}