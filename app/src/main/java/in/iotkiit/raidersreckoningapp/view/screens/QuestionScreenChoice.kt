package `in`.iotkiit.raidersreckoningapp.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import `in`.iotkiit.raidersreckoningapp.data.model.Question
import `in`.iotkiit.raidersreckoningapp.data.model.QuestionData
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.ui.theme.modernWarfare
import `in`.iotkiit.raidersreckoningapp.view.components.core.useGlobalTimer
import `in`.iotkiit.raidersreckoningapp.view.components.myTeam.Fields
import `in`.iotkiit.raidersreckoningapp.view.components.questions.CircularTimer
import `in`.iotkiit.raidersreckoningapp.view.components.questions.ClickableFields
import kotlinx.coroutines.delay

@Composable
fun QuestionScreenChoice(
    question: Question,
    questionData: QuestionData,
    navController: NavController,
    onNext: (Int, String) -> Unit,
) {
    var selectedOption by remember(question.id) { mutableStateOf<String?>(null) }
    var timer by remember(question.id) { mutableIntStateOf(question.timeAlloted) }
    var hasSubmitted by remember(question.id) { mutableStateOf(false) }

    val (minutes, seconds, _) = useGlobalTimer(
        zoneStartTime = questionData.zoneStartTime, zoneDuration = questionData.zoneDuration
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
            onNext(0, selectedOption ?: "")
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                question.mcqAnswers.forEach { option ->
                    ClickableFields(field = option,
                        containerColor = if (selectedOption == option) GreenCOD else GreenCOD.copy(
                            alpha = 0.1f
                        ),
                        contentColor = if (selectedOption == option) Color.Black else Color.White,
                        borderColor = if (selectedOption == option) GreenCOD else Color.DarkGray,
                        borderWidth = 2.dp,
                        onClick = {
                            if (!hasSubmitted) {
                                selectedOption = option
                            }
                        })
                }
            }
            Button(
                onClick = {
                    if (!hasSubmitted) {
                        hasSubmitted = true
                        onNext(timer, selectedOption ?: "")
                    }
                }, enabled = !hasSubmitted, colors = ButtonDefaults.buttonColors(
                    containerColor = GreenCOD, disabledContainerColor = GreenCOD.copy(alpha = 0.5f)
                ), modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text("Next", fontFamily = modernWarfare, fontSize = 24.sp, color = Color.Black)
            }
        }
    }
}
