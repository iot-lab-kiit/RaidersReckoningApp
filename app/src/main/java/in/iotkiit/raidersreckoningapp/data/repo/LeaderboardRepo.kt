package `in`.iotkiit.raidersreckoningapp.data.repo

import coil.network.HttpException
import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.GetLeaderboardResponse
import `in`.iotkiit.raidersreckoningapp.data.remote.LeaderboardApi
import `in`.iotkiit.raidersreckoningapp.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

class LeaderboardRepo @Inject constructor(
    private val leaderboardApi: LeaderboardApi
) {

    suspend fun getLeaderboardData(
        accessToken: String
    ): Flow<UiState<CustomResponse<GetLeaderboardResponse>>> {
        return flow {
            try {
                emit(UiState.Loading)

                val response: CustomResponse<GetLeaderboardResponse> =
                    leaderboardApi.getLeaderboard(accessToken)

                if (response.success) {
                    emit(UiState.Success(response))
                } else {
                    emit(UiState.Failed(response.message ?: "Failed to fetch leaderboard data"))
                }
            } catch (e: UnknownHostException) {
                emit(UiState.Failed("No Internet Connection!"))
            } catch (e: HttpException) {
                emit(UiState.Failed("Server error. Please try again later."))
            } catch (e: Exception) {
                emit(UiState.Failed(e.message ?: "Something went wrong."))
            }
        }
    }

}