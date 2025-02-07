package `in`.iotkiit.raidersreckoningapp.data.repo

import `in`.iotkiit.raidersreckoningapp.data.model.CreateTeamBody
import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.JoinTeamBody
import `in`.iotkiit.raidersreckoningapp.data.model.Question
import `in`.iotkiit.raidersreckoningapp.data.model.TeamInfo
import `in`.iotkiit.raidersreckoningapp.data.remote.TeamApi
import `in`.iotkiit.raidersreckoningapp.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TeamRepo @Inject constructor(
    private val teamApi: TeamApi
) {
    suspend fun createTeam(
        accessToken: String,
        createTeamBody: CreateTeamBody
    ): Flow<UiState<CustomResponse<TeamInfo>>> {
        return flow {
            try {
                emit(UiState.Loading)

                val response: CustomResponse<TeamInfo> =
                    teamApi.createTeam(accessToken, createTeamBody)

                if (response.success) {
                    emit(UiState.Success(response))
                } else {
                    emit(UiState.Failed(response.message ?: "Failed to create team"))
                }
            } catch (e: Exception) {
                emit(UiState.Failed(e.message.toString()))
            }
        }
    }

    suspend fun joinTeam(
        accessToken: String,
        joinTeamBody: JoinTeamBody
    ): Flow<UiState<CustomResponse<Unit>>> {
        return flow {
            try {
                emit(UiState.Loading)

                val response: CustomResponse<Unit> =
                    teamApi.joinTeam(accessToken, joinTeamBody)

                if (response.success) {
                    emit(UiState.Success(response))
                } else {
                    emit(UiState.Failed(response.message ?: "Failed to join team"))
                }
            } catch (e: Exception) {
                emit(UiState.Failed(e.message.toString()))
            }
        }
    }

    suspend fun getQuestions(accessToken: String): Flow<UiState<CustomResponse<List<Question>>>> {
        return flow {
            try {
                emit(UiState.Loading)

                val response = teamApi.getQuestions(accessToken)

                if (response.success) {
                    emit(UiState.Success(response))
                } else {
                    emit(UiState.Failed(response.message ?: "Failed to get questions"))
                }
            } catch (e: Exception) {
                emit(UiState.Failed(e.message.toString()))
            }
        }
    }
}
