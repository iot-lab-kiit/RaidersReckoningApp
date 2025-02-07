package `in`.iotkiit.raidersreckoningapp.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iotkiit.raidersreckoningapp.data.model.CreateTeamBody
import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.JoinTeamBody
import `in`.iotkiit.raidersreckoningapp.data.model.TeamInfo
import `in`.iotkiit.raidersreckoningapp.data.repo.TeamRepo
import `in`.iotkiit.raidersreckoningapp.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val teamRepo: TeamRepo
): ViewModel() {

    private val _createTeamState: MutableStateFlow<UiState<CustomResponse<TeamInfo>>> =
        MutableStateFlow(UiState.Idle)

    val createTeamState = _createTeamState.asStateFlow()

    fun createTeam(createTeamBody: CreateTeamBody) {
        _createTeamState.value = UiState.Loading
        viewModelScope.launch {
            try {
                teamRepo.createTeam("Bearer Token", createTeamBody).collect { response ->
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
                teamRepo.joinTeam("Bearer token", joinTeamBody)
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
}