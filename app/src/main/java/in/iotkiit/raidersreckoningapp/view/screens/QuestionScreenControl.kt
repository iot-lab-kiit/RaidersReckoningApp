package `in`.iotkiit.raidersreckoningapp.view.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import `in`.iotkiit.raidersreckoningapp.data.model.SubmitPointsBody
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.view.components.anims.FailureAnimationDialog
import `in`.iotkiit.raidersreckoningapp.view.components.anims.SubmitPointsAnimation
import `in`.iotkiit.raidersreckoningapp.view.components.core.useGlobalTimer
import `in`.iotkiit.raidersreckoningapp.view.navigation.RaidersReckoningScreens
import `in`.iotkiit.raidersreckoningapp.vm.TeamViewModel
import kotlinx.coroutines.delay

@Composable
fun QuestionScreenControl(
    navController: NavController,
    zoneId: String,
    viewModel: TeamViewModel = hiltViewModel()
) {
    val questionsState = viewModel.getQuestionsState.collectAsState().value
    var currentIndex by remember { mutableIntStateOf(0) }
    val submitPointsState = viewModel.submitPointsState.collectAsState().value
    val context = LocalContext.current
    var showEndAnimation by remember { mutableStateOf(false) }
    var submissionFailed by remember { mutableStateOf(false) }

    LaunchedEffect(submitPointsState) {
        when (submitPointsState) {
            is UiState.Success -> {
                submissionFailed = false
                showEndAnimation = true
            }

            is UiState.Failed -> {
                submissionFailed = true
                showEndAnimation = false
                Toast.makeText(
                    context,
                    submitPointsState.message,
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate(RaidersReckoningScreens.DashBoardScreen.route) {
                    popUpTo(RaidersReckoningScreens.QuestionScreen.route) { inclusive = true }
                }
            }

            else -> {
                submissionFailed = false
                showEndAnimation = false
            }
        }
    }

    // Show end animation only when quiz is complete AND submission was successful
    if (showEndAnimation && !submissionFailed) {
        SubmitPointsAnimation(
            onAnimationComplete = {
                navController.navigate(RaidersReckoningScreens.DashBoardScreen.route) {
                    popUpTo(RaidersReckoningScreens.QuestionScreen.route) { inclusive = true }
                }
            }
        )
    }

    when (questionsState) {
        is UiState.Idle -> {
            viewModel.getQuestions(zoneId)
        }

        is UiState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LinearProgressIndicator(color = GreenCOD)
            }
        }

        is UiState.Failed -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                FailureAnimationDialog(
                    message = questionsState.message,
                    onTryAgainClick = {
                        viewModel.getQuestions(zoneId)
                    }
                )
            }
        }

        is UiState.Success -> {
            val questionData = questionsState.data.data
            if (questionData == null || questionData.questions.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No questions available", color = Color.White, fontSize = 18.sp)
                }
                Toast.makeText(context, "No questions available", Toast.LENGTH_SHORT).show()
                return
            }

            // Handle global timer
            val (_, _, isTimeUp) = useGlobalTimer(
                zoneStartTime = questionData.zoneStartTime,
                zoneDuration = questionData.zoneDuration
            )

            // Modified time up handling to show animation
            LaunchedEffect(isTimeUp) {
                if (isTimeUp) {
                    // Submit answers for all remaining questions
                    for (i in currentIndex until questionData.questions.size) {
                        val remainingQuestion = questionData.questions[i]
                        viewModel.submitAnswer(remainingQuestion, 0, "")
                        delay(100)
                    }

                    viewModel.submitPoints(
                        SubmitPointsBody(tempPoints = viewModel.getTotalPoints())
                    )
                    showEndAnimation = true
                    return@LaunchedEffect
                }
            }

            val currentQuestion = questionData.questions[currentIndex]
            val isOneWord = questionData.questions.first().oneWord

            if (!isTimeUp) {
                if (isOneWord) {
                    QuestionScreen(
                        question = currentQuestion,
                        questionData = questionData,
                        navController = navController,
                        onNext = { remainingTime, answer ->
                            viewModel.submitAnswer(currentQuestion, remainingTime, answer)
                            handleNavigation(
                                currentIndex,
                                questionData.questions.size - 1,
                                onNextQuestion = { currentIndex++ },
                                onComplete = { showEndAnimation = true }
                            )
                        }
                    )
                } else {
                    QuestionScreenChoice(
                        question = currentQuestion,
                        questionData = questionData,
                        navController = navController,
                        onNext = { remainingTime, selectedOption ->
                            viewModel.submitAnswer(currentQuestion, remainingTime, selectedOption)
                            handleNavigation(
                                currentIndex,
                                questionData.questions.size - 1,
                                onNextQuestion = { currentIndex++ },
                                onComplete = { showEndAnimation = true }
                            )
                        }
                    )
                }
            }
        }
    }
}

// Modified navigation handling
private fun handleNavigation(
    currentIndex: Int,
    lastIndex: Int,
    onNextQuestion: () -> Unit,
    onComplete: () -> Unit
) {
    if (currentIndex < lastIndex) {
        onNextQuestion()
    } else {
        onComplete()
    }
}