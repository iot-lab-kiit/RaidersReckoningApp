package `in`.iotkiit.raidersreckoningapp.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import `in`.iotkiit.raidersreckoningapp.data.model.Question
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.ui.theme.modernWarfare
import `in`.iotkiit.raidersreckoningapp.view.components.questions.CircularTimer
import `in`.iotkiit.raidersreckoningapp.view.components.myTeam.Fields
import kotlinx.coroutines.delay

@Composable
fun QuestionScreen(
    question: Question,
    navController: NavController,
    onNext: (Int, String) -> Unit,
) {
    var userAnswer by remember { mutableStateOf(TextFieldValue()) }
    var timer by remember { mutableIntStateOf(question.timeAlloted) }
    var hasSubmitted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (timer > 0 && !hasSubmitted) {
            delay(1000L)
            timer--
        }
        // Auto-submit when timer reaches 0
        if (!hasSubmitted) {
            hasSubmitted = true
            onNext(0, userAnswer.text)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            CircularTimer(currentTime = timer, totalTime = question.duration)
            Fields(field = question.question)
            OutlinedTextField(
                value = userAnswer,
                onValueChange = {
                    userAnswer = it
                },
                placeholder = {
                    Text(
                        "Enter Answer",
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .border(2.dp, Color.Green, RoundedCornerShape(50))
                    .fillMaxWidth(0.7f),
                shape = RoundedCornerShape(50),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White.copy(0.6f),
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White
                )
            )

            Button(
                onClick = {
                    if (!hasSubmitted) {
                        hasSubmitted = true
                        onNext(timer, userAnswer.text)
                    }
                },
                enabled = !hasSubmitted,
                colors = ButtonDefaults.buttonColors(
                    containerColor = GreenCOD,
                    disabledContainerColor = GreenCOD.copy(alpha = 0.5f)
                ),
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text("Next", fontFamily = modernWarfare, fontSize = 24.sp, color = Color.Black)
            }
        }
    }
}

