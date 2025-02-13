package `in`.iotkiit.raidersreckoningapp.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iotkiit.raidersreckoningapp.data.model.CreateTeamBody
import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.GetTeamResponse
import `in`.iotkiit.raidersreckoningapp.data.model.JoinTeamBody
import `in`.iotkiit.raidersreckoningapp.data.model.Question
import `in`.iotkiit.raidersreckoningapp.data.model.QuestionData
import `in`.iotkiit.raidersreckoningapp.data.model.SubmitPointsBody
import `in`.iotkiit.raidersreckoningapp.data.repo.TeamRepo
import `in`.iotkiit.raidersreckoningapp.state.PreferencesHelper
import `in`.iotkiit.raidersreckoningapp.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val teamRepo: TeamRepo,
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {

    private val _createTeamState: MutableStateFlow<UiState<CustomResponse<Unit>>> =
        MutableStateFlow(UiState.Idle)
    val createTeamState = _createTeamState.asStateFlow()

    fun createTeam(createTeamBody: CreateTeamBody) {
        _createTeamState.value = UiState.Loading
        viewModelScope.launch {
            try {
                teamRepo.createTeam(teamRepo.getIdToken(), createTeamBody)
                    .collect { response ->
                        _createTeamState.value = response
                        if (response is UiState.Success) {
                            Log.d("TeamViewModel", "Team Created successfully : ${response.data}")
                        }
                    }
            } catch (e: Exception) {
                Log.d("TeamViewModel", "createTeam: ${e.message}")
                _createTeamState.value = UiState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    private val _getTeamState: MutableStateFlow<UiState<CustomResponse<GetTeamResponse>>> =
        MutableStateFlow(UiState.Idle)
    val getTeamState = _getTeamState.asStateFlow()

    fun getTeam() {
        _getTeamState.value = UiState.Loading
        viewModelScope.launch {
            try {
                teamRepo.getTeam(teamRepo.getIdToken())
                    .collect { response ->
                        _getTeamState.value = response
                        if (response is UiState.Success) {
                            Log.d("TeamViewModel", "Team fetched successfully: ${response.data}")
                        }
                    }
            } catch (e: Exception) {
                Log.d("TeamViewModel", "getTeam: ${e.message}")
                _getTeamState.value = UiState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun resetGetTeamState() {
        _getTeamState.value = UiState.Idle
    }

    fun resetCreateTeamState() {
        _createTeamState.value = UiState.Idle
    }

    private val _joinTeamState: MutableStateFlow<UiState<CustomResponse<Unit>>> =
        MutableStateFlow(UiState.Idle)
    val joinTeamState = _joinTeamState.asStateFlow()

    fun joinTeam(joinTeamBody: JoinTeamBody) {
        _joinTeamState.value = UiState.Loading
        viewModelScope.launch {
            try {
                teamRepo.joinTeam(teamRepo.getIdToken(), joinTeamBody)
                    .collect { response ->
                        _joinTeamState.value = response
                        if (response is UiState.Success) {
                            Log.d("TeamViewModel", "Team joined successfully: ${response.data}")
                        }
                    }
            } catch (e: Exception) {
                Log.d("TeamViewModel", "joinTeam: ${e.message}")
                _joinTeamState.value = UiState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun resetJoinTeamState() {
        _joinTeamState.value = UiState.Idle
    }

    private val _getQuestionsState: MutableStateFlow<UiState<CustomResponse<QuestionData>>> =
        MutableStateFlow(UiState.Idle)
    val getQuestionsState = _getQuestionsState.asStateFlow()

    fun getQuestions(zoneId: String) {
        _getQuestionsState.value = UiState.Loading
        viewModelScope.launch {
            try {
                teamRepo.getQuestions(teamRepo.getIdToken(), zoneId)
                    .collect { response ->
                        _getQuestionsState.value = response
                        if (response is UiState.Success) {
                            preferencesHelper.resetPoints()
                            Log.d(
                                "TeamViewModel",
                                "Questions fetched successfully: ${response.data}"
                            )
                        }
                    }
            } catch (e: UnknownHostException) {
                Log.d("TeamViewModel", "No Internet Connection")
                _getQuestionsState.value =
                    UiState.Failed("No internet connection. Check your network and try again.")
            } catch (e: HttpException) {
                Log.d("TeamViewModel", "getQuestions: ${e.message}")
                _getQuestionsState.value = UiState.Failed("Server error. Try again later.")
            } catch (e: Exception) {
                Log.d("TeamViewModel", "getQuestions: ${e.message}")
                _getQuestionsState.value = UiState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun resetGetQuestionsState() {
        _getQuestionsState.value = UiState.Idle
    }

    fun submitAnswer(
        question: Question,
        remainingTime: Int,
        userAnswer: String
    ) {
        val isCorrect = if (question.oneWord) {
            userAnswer.trim().equals(question.mcqAnswers.first().toString(), ignoreCase = true)
        } else {
            val correctIndex = question.correctAnswer
            correctIndex in question.mcqAnswers.indices &&
                    userAnswer == question.mcqAnswers[correctIndex]
        }

        if (isCorrect) {
            // Calculate points for this question
            val questionPoints = question.multiplier * remainingTime

            // Log for debugging
            Log.d("Points", "Multiplier: ${question.multiplier}")
            Log.d("Points", "Remaining Time: $remainingTime")
            Log.d("Points", "Question Points: $questionPoints")

            // Save these points which will add to total
            preferencesHelper.savePoints(questionPoints)

            // Log total points after adding
            Log.d("Points", "Total Points after adding: ${preferencesHelper.getTotalPoints()}")
        }

        val questions = (_getQuestionsState.value as? UiState.Success)?.data?.data

        // Check if this was the last question
        if (!questions?.questions.isNullOrEmpty() && questions?.questions?.last()?.id == question.id) {
            val finalTotalPoints = preferencesHelper.getTotalPoints()
            Log.d("Points", "Final Total Points: $finalTotalPoints")

            submitPoints(
                SubmitPointsBody(tempPoints = finalTotalPoints)
            )
        }
    }

    fun getTotalPoints(): Int = preferencesHelper.getTotalPoints()

    private val _submitPointsState: MutableStateFlow<UiState<CustomResponse<Unit>>> =
        MutableStateFlow(UiState.Idle)
    val submitPointsState = _submitPointsState.asStateFlow()

    fun submitPoints(submitPointsBody: SubmitPointsBody) {
        _submitPointsState.value = UiState.Loading
        viewModelScope.launch {
            try {
                teamRepo.submitPoints(teamRepo.getIdToken(), submitPointsBody)
                    .collect { response ->
                        _submitPointsState.value = response
                        if (response is UiState.Success) {
                            Log.d(
                                "TeamViewModel",
                                "Points submitted successfully: ${response.data}"
                            )
                        }
                    }
            } catch (e: Exception) {
                Log.d("TeamViewModel", "submitPoints: ${e.message}")
                _submitPointsState.value = UiState.Failed(e.message ?: "Unknown error")
            }
        }
    }
}