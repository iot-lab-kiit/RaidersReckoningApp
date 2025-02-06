package `in`.iotkiit.raidersreckoningapp.data.repo

import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.LeaderboardData
import `in`.iotkiit.raidersreckoningapp.data.remote.LeaderboardApi
import `in`.iotkiit.raidersreckoningapp.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LeaderboardRepo @Inject constructor(
    private val leaderboardApi: LeaderboardApi
) {

    suspend fun getLeaderboardData(
        accessToken: String
    ): Flow<UiState<CustomResponse<LeaderboardData>>> {
        return flow {
            try {
                emit(UiState.Loading)

                val response: CustomResponse<LeaderboardData> =
                    leaderboardApi.getLeaderboard(accessToken)

                if (response.success) {
                    emit(UiState.Success(response))
                } else {
                    emit(UiState.Failed(response.message ?: "Failed to fetch leaderboard data"))
                }
            } catch (e: Exception) {
                emit(UiState.Failed(e.message.toString()))
            }
        }
    }

}