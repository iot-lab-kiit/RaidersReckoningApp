package `in`.iotkiit.raidersreckoningapp.data.repo

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.DashboardResponse
import `in`.iotkiit.raidersreckoningapp.data.remote.DashboardApi
import `in`.iotkiit.raidersreckoningapp.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DashBoardRepo @Inject constructor(
    private val dashboardApi: DashboardApi,
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


    suspend fun getDashboardData(
        accessToken: String
    ): Flow<UiState<CustomResponse<DashboardResponse>>> {
        return flow {
            try {
                emit(UiState.Loading)

                val response: CustomResponse<DashboardResponse> =
                    dashboardApi.getDashboard(accessToken)

                if (response.success) {
                    emit(UiState.Success(response))
                } else {
                    emit(UiState.Failed(response.message ?: "Failed to fetch dashboard data"))
                }
            } catch (e: Exception) {
                emit(UiState.Failed(e.message.toString()))
            }
        }
    }

    suspend fun verifyToken(
        accessToken: String
    ): Flow<UiState<CustomResponse<Unit>>> {
        return flow {
            try {
                emit(UiState.Loading)

                val response = dashboardApi.verifyToken(accessToken)
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

}