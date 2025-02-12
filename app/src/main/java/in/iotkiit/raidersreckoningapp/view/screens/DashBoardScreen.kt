package `in`.iotkiit.raidersreckoningapp.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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

@Composable
fun DashBoardScreen(
    navController: NavController, viewModel: DashBoardViewModel = hiltViewModel()
) {

    val getDashBoardState = viewModel.getDashBoardState.collectAsState().value

    Scaffold(containerColor = Color.Black, bottomBar = {
        BottomNavBar(navController = navController, bottomMenu = bottomNavOptions)
    }, topBar = {
        TopBar(
            modifier = Modifier.fillMaxWidth(), teamName = "TaskForce141", points = 10
        )
    }) {

        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            MapCard(
                mapImage = painterResource(R.drawable.map),
                soldierImage = painterResource(R.drawable.soldier)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        space = 24.dp, alignment = Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "NUKETOWN",
                        style = MaterialTheme.typography.displayMedium,
                        color = Color.White
                    )
                    Text(
                        modifier = Modifier,
                        text = "C001",
                        style = MaterialTheme.typography.labelSmall,
                        color = GreenCOD
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Round 1",
                        style = MaterialTheme.typography.bodyMedium,
                        color = GreenCOD
                    )

                    Text(
                        color = Color.White,
                        text = "30:00",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = GreenCOD,
                contentColor = Color.Black,
            ) {
                Image(
                    modifier = Modifier.padding(8.dp),
                    painter = painterResource(R.drawable.scan),
                    contentDescription = null
                )
            }
        }
    }

}