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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.ui.theme.modernWarfare
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
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Spacer(modifier = Modifier.height(96.dp))

                MapCard(
                    modifier = Modifier.padding(16.dp),
                    mapImage = painterResource(R.drawable.map),
                    soldierImage = painterResource(R.drawable.soldier)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "NUKETOWN",
                        fontFamily = modernWarfare,
                        fontSize = 32.sp,
                        color = Color.White
                    )

                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "C001",
                        fontFamily = modernWarfare,
                        fontSize = 20.sp,
                        color = GreenCOD
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Round 1",
                        fontFamily = modernWarfare,
                        fontSize = 32.sp,
                        color = GreenCOD
                    )

                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "30:00",
                        fontFamily = modernWarfare,
                        fontSize = 32.sp,
                        color = Color.White
                    )
                }

                FloatingActionButton(
                    onClick = { /*TODO*/ },
                    containerColor = GreenCOD,
                    contentColor = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Image(
                        painter = painterResource(R.drawable.scan),
                        contentDescription = null
                    )
                }

            }
        }
    }

}