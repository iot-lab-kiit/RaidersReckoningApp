package `in`.iotkiit.raidersreckoningapp.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.DashboardResponse
import `in`.iotkiit.raidersreckoningapp.data.repo.DashBoardRepo
import `in`.iotkiit.raidersreckoningapp.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val dashBoardRepo: DashBoardRepo
) : ViewModel() {

    private val _getDashBoardState: MutableStateFlow<UiState<CustomResponse<DashboardResponse>>> =
        MutableStateFlow(UiState.Idle)
    val getDashBoardState = _getDashBoardState.asStateFlow()

    fun getDashBoardData() {
        _getDashBoardState.value = UiState.Loading
        viewModelScope.launch {
            try {
                dashBoardRepo.getDashboardData(dashBoardRepo.getIdToken())
                    .collect { response ->
                        _getDashBoardState.value = response
                        if (response is UiState.Success) {
                            Log.d(
                                "DashBoardViewModel",
                                "LeaderBoard Data fetched: ${response.data}"
                            )
                        }
                    }
            } catch (e: Exception) {
                Log.d("DashBoardViewModel", "LeaderBoardData: ${e.message}")
                _getDashBoardState.value = UiState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    private val _verifyTokenState: MutableStateFlow<UiState<CustomResponse<Unit>>> =
        MutableStateFlow(UiState.Idle)
    val verifyTokenState = _verifyTokenState.asStateFlow()

    fun verifyToken(accessToken: String) {
        _verifyTokenState.value = UiState.Loading
        viewModelScope.launch {
            try {
                dashBoardRepo.verifyToken("Bearer $accessToken")
                    .collect { response ->
                        _verifyTokenState.value = response
                        if (response is UiState.Success) {
                            Log.d("DashBoardViewModel", "Token verified: ${response.data}")
                        }
                    }
            } catch (e: Exception) {
                Log.d("DashBoardViewModel", "TokenVerification: ${e.message}")
                _verifyTokenState.value = UiState.Failed(e.message ?: "Unknown error")
            }
        }
    }

    fun resetVerifyTokenState(){
        _verifyTokenState.value = UiState.Loading
    }
}