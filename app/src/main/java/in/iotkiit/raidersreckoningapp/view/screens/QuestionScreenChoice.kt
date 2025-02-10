package `in`.iotkiit.raidersreckoningapp.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.ui.theme.modernWarfare
import `in`.iotkiit.raidersreckoningapp.view.components.myTeam.Fields
import `in`.iotkiit.raidersreckoningapp.vm.TeamViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QuestionScreenChoice(
    navController: NavController,
    viewModel: TeamViewModel = hiltViewModel()
) {
    val timer = remember { mutableStateOf(10) }
    val question = "Which weapon is best for long-range combat in COD?"
    val options = listOf("Assault Rifle", "Sniper Rifle", "Shotgun", "SMG")
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize().background(Color.Black),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Time: ${timer.value} s",
                fontFamily = modernWarfare,
                fontSize = 32.sp,
                color = GreenCOD,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = question,
                fontFamily = modernWarfare,
                fontSize = 28.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                options.forEach { option ->
                    Fields(
                        field = option,
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* Handle next question */ },
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenCOD)
            ) {
                Text(
                    text = "Next",
                    fontFamily = modernWarfare,
                    fontSize = 24.sp,
                    color = Color.Black
                )
            }
        }
    }
}
