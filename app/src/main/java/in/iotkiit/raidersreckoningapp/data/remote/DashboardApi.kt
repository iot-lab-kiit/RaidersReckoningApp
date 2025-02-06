package `in`.iotkiit.raidersreckoningapp.data.remote

import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.DashboardResponse
import `in`.iotkiit.raidersreckoningapp.data.util.Constants
import retrofit2.http.GET
import retrofit2.http.Header

interface DashboardApi {

    @GET(Constants.GET_DASHBOARD_ENDPOINT)
    suspend fun getDashboard(
        @Header("Authorization") accessToken: String
    ): CustomResponse<DashboardResponse>

    @GET(Constants.VERIFY_TOKEN_ENDPOINT)
    suspend fun verifyToken(
        @Header("Authorization") accessToken: String
    ): CustomResponse<Unit>
}