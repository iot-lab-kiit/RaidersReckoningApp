package `in`.iotkiit.raidersreckoningapp.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.GetLeaderboardResponse
import `in`.iotkiit.raidersreckoningapp.data.repo.DashBoardRepo
import `in`.iotkiit.raidersreckoningapp.data.repo.LeaderboardRepo
import `in`.iotkiit.raidersreckoningapp.data.repo.TeamRepo
import `in`.iotkiit.raidersreckoningapp.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val leaderboardRepo: LeaderboardRepo,
    private val dashboardRepo: DashBoardRepo,
    private val teamRepo: TeamRepo
) : ViewModel() {

    private val _getLeaderboardDataState: MutableStateFlow<UiState<CustomResponse<GetLeaderboardResponse>>> =
        MutableStateFlow(UiState.Idle)
    val getLeaderboardData = _getLeaderboardDataState.asStateFlow()

    fun getLeaderboardData() {
        _getLeaderboardDataState.value = UiState.Loading
        viewModelScope.launch {
            try {
                leaderboardRepo.getLeaderboardData(teamRepo.getIdToken())
                    .collect { response ->
                        _getLeaderboardDataState.value = response
                        if (response is UiState.Success) {
                            Log.d(
                                "LeaderboardViewModel",
                                "leaderboard data fetched: ${response.data}"
                            )
                        }
                    }
            } catch (e: Exception) {
                Log.d("LeaderboardViewModel", "getLeaderboardData: ${e.message}")
                _getLeaderboardDataState.value = UiState.Failed(e.message ?: "Unknown error")
            }
        }
    }

}