package `in`.iotkiit.raidersreckoningapp.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.ui.theme.modernWarfare
import `in`.iotkiit.raidersreckoningapp.view.components.myTeam.Fields
import `in`.iotkiit.raidersreckoningapp.vm.TeamViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun QuestionScreen(
    navController: NavController,
    viewModel: TeamViewModel = hiltViewModel()
) {
    val timer = remember { mutableStateOf(10) }
    val question = "Describe the best strategy for long-range combat in COD?"
    var userAnswer by remember { mutableStateOf(TextFieldValue()) }

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


            Fields(
                field = question,
            )

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = userAnswer,
                onValueChange = { userAnswer = it },
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp, color = Color.White),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .background(Color.DarkGray, MaterialTheme.shapes.medium),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.DarkGray,
                    focusedIndicatorColor = GreenCOD,
                    unfocusedIndicatorColor = Color.Gray
                ),
                placeholder = { Text("Type your answer here...", color = Color.Gray) }
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* Handle answer submission */ },
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
