package `in`.iotkiit.raidersreckoningapp.data.repo

import coil.network.HttpException
import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.DashboardResponse
import `in`.iotkiit.raidersreckoningapp.data.remote.DashboardApi
import `in`.iotkiit.raidersreckoningapp.state.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

class DashBoardRepo @Inject constructor(
    private val dashboardApi: DashboardApi,
) {

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