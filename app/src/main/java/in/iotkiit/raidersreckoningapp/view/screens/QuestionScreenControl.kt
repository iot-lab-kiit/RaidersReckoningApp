package `in`.iotkiit.raidersreckoningapp.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.view.navigation.RaidersReckoningScreens
import `in`.iotkiit.raidersreckoningapp.vm.TeamViewModel

@Composable
fun QuestionScreenControl(
    navController: NavController,
    viewModel: TeamViewModel = hiltViewModel()
) {
    val uiState = viewModel.getQuestionsState.collectAsState().value
    var currentIndex by remember { mutableIntStateOf(0) }

    when (uiState) {
        is UiState.Idle -> {
            viewModel.getQuestions("5af1c7a4-2232-46d3-b9c8-e37ed1703cdf")
        }

        is UiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = GreenCOD)
            }
        }

        is UiState.Failed -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error: ${uiState.message}", color = Color.Red, fontSize = 18.sp)
            }
        }

        is UiState.Success -> {
            val questionData = uiState.data.data
            if (questionData != null) {
                if (questionData.questions.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No questions available", color = Color.White, fontSize = 18.sp)
                    }
                    return
                }
            }

            val currentQuestion = questionData?.questions?.get(currentIndex)
            val isOneWord = questionData?.questions?.first()?.oneWord

            if (isOneWord == true) {
                if (currentQuestion != null) {
                    QuestionScreen(
                        question = currentQuestion,
                        navController = navController,
                        onNext = { remainingTime, answer ->
                            viewModel.submitAnswer(currentQuestion, remainingTime, answer)
                            handleNavigation(
                                currentIndex,
                                questionData.questions.size - 1,
                                navController
                            ) {
                                currentIndex++
                            }
                        }
                    )
                }
            } else {
                if (currentQuestion != null) {
                    QuestionScreenChoice(
                        question = currentQuestion,
                        navController = navController,
                        onNext = { remainingTime, selectedOption ->
                            viewModel.submitAnswer(currentQuestion, remainingTime, selectedOption)
                            handleNavigation(
                                currentIndex,
                                questionData.questions.size - 1,
                                navController
                            ) {
                                currentIndex++
                            }
                        }
                    )
                }
            }
        }
    }
}

private fun handleNavigation(
    currentIndex: Int,
    lastIndex: Int,
    navController: NavController,
    onNextQuestion: () -> Unit
) {
    if (currentIndex < lastIndex) {
        onNextQuestion()
    } else {
        navController.navigate(RaidersReckoningScreens.ResultsScreen.route) {
            popUpTo(RaidersReckoningScreens.QuestionScreen.route) { inclusive = true }
        }
    }
}

//val questions = listOf(
//    Question(
//        id = "1",
//        question = "What is the capital of France?",
//        multiplier = 1,
//        timeAlloted = 5,
//        mcqAnswers = listOf("Paris","France","Japan", "China"),
//        correctAnswer = 0,
//        round = 1,
//        duration = 5,
//        oneWord = false,
//        createdAt = "2025-02-11T10:00:00Z",
//        updatedAt = "2025-02-11T10:00:00Z"
//    ),
//    Question(
//        id = "2",
//        question = "Solve: 12 × 8",
//        multiplier = 1,
//        timeAlloted = 10,
//        mcqAnswers = listOf("96", "108", "88", "102"),
//        correctAnswer = 0,
//        round = 1,
//        duration = 10,
//        oneWord = false,
//        createdAt = "2025-02-11T10:01:00Z",
//        updatedAt = "2025-02-11T10:01:00Z"
//    ),
//    Question(
//        id = "3",
//        question = "Which planet is known as the Red Planet?",
//        multiplier = 1,
//        timeAlloted = 5,
//        mcqAnswers = listOf("Earth", "Mars", "Jupiter", "Saturn"),
//        correctAnswer = 1,
//        round = 2,
//        duration = 5,
//        oneWord = false,
//        createdAt = "2025-02-11T10:02:00Z",
//        updatedAt = "2025-02-11T10:02:00Z"
//    )
//)
