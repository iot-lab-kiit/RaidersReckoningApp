package `in`.iotkiit.raidersreckoningapp.data.remote

import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.DashboardData
import `in`.iotkiit.raidersreckoningapp.data.util.Constants
import retrofit2.http.GET
import retrofit2.http.Header

interface DashboardApi {
    @GET(Constants.GET_DASHBOARD_ENDPOINT)
    suspend fun getDashDoarditems(
        @Header("Authorization") accessToken: String,
    ): CustomResponse<DashboardData>
}