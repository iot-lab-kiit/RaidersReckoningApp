package `in`.iotkiit.raidersreckoningapp.data.repo

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import `in`.iotkiit.raidersreckoningapp.data.model.CreateTeamBody
import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.GetTeamResponse
import `in`.iotkiit.raidersreckoningapp.data.model.JoinTeamBody
import `in`.iotkiit.raidersreckoningapp.data.model.Question
import `in`.iotkiit.raidersreckoningapp.data.model.TeamInfo
import `in`.iotkiit.raidersreckoningapp.data.remote.TeamApi
import `in`.iotkiit.raidersreckoningapp.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TeamRepo @Inject constructor(
    private val teamApi: TeamApi,
    private val auth: FirebaseAuth
) {

    suspend fun getIdToken(): String {
        return try {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val idTokenResult = currentUser.getIdToken(true).await()
                val token = idTokenResult?.token ?: "No Token Found"
                Log.d("AuthRepo", token)
                "Bearer $token"
            } else {
                "No Token Found"
            }
        } catch (e: Exception) {
            Log.e("AuthRepo", "Error getting token", e)
            "No Token Found"
        }
    }

    suspend fun verifyToken(
        accessToken: String
    ): Flow<UiState<CustomResponse<Unit>>> {
        return flow {
            try {
                emit(UiState.Loading)

                val response = teamApi.verifyToken(accessToken)
                if (response.success) {
                    emit(UiState.Success(response))
                } else {
                    emit(UiState.Failed(response.message ?: "Failed to verify token"))
                }
            } catch (e: Exception) {
                emit(UiState.Failed(e.message.toString()))
            }
        }
    }

    suspend fun getTeam(accessToken: String): Flow<UiState<CustomResponse<GetTeamResponse>>> {
        return flow {
            try {
                emit(UiState.Loading)

                val response = teamApi.getTeam(accessToken)

                if (response.success) {
                    emit(UiState.Success(response))
                } else {
                    emit(UiState.Failed(response.message ?: "Failed to get team"))
                }
            } catch (e: Exception) {
                emit(UiState.Failed(e.message.toString()))
            }
        }
    }

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