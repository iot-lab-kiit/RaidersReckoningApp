package `in`.iotkiit.raidersreckoningapp.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import `in`.iotkiit.raidersreckoningapp.data.model.Question
import `in`.iotkiit.raidersreckoningapp.data.model.QuestionData
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.ui.theme.modernWarfare
import `in`.iotkiit.raidersreckoningapp.view.components.core.CustomOutlinedTextField
import `in`.iotkiit.raidersreckoningapp.view.components.core.useGlobalTimer
import `in`.iotkiit.raidersreckoningapp.view.components.myTeam.Fields
import `in`.iotkiit.raidersreckoningapp.view.components.questions.CircularTimer
import kotlinx.coroutines.delay

@Composable
fun QuestionScreen(
    question: Question,
    questionData: QuestionData,
    navController: NavController,
    onNext: (Int, String) -> Unit,
) {
    var userAnswer by remember(question.id) { mutableStateOf(TextFieldValue()) }
    var timer by remember(question.id) { mutableIntStateOf(question.timeAlloted) }
    var hasSubmitted by remember(question.id) { mutableStateOf(false) }

    val (minutes, seconds, _) = useGlobalTimer(
        zoneStartTime = questionData.zoneStartTime,
        zoneDuration = questionData.zoneDuration
    )

    LaunchedEffect(question.id) {
        // Reset states for new question
        timer = question.duration
        hasSubmitted = false

        while (timer > 0 && !hasSubmitted) {
            delay(1000L)
            timer--
        }

        // Auto-submit when timer reaches 0
        if (!hasSubmitted && timer == 0) {
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
            Text(
                text = String.format("%02d:%02d", minutes, seconds),
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = modernWarfare
            )
            CircularTimer(currentTime = timer, totalTime = question.duration)
            Fields(field = question.question)

            CustomOutlinedTextField(
                value = userAnswer,
                onValueChange = {
                    if (!hasSubmitted) {
                        userAnswer = it
                    }
                },
                placeholder = "Enter Answer",
                enabled = !hasSubmitted,
                textStyle = MaterialTheme.typography.bodyMedium
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

