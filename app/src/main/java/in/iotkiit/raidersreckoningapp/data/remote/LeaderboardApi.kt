package `in`.iotkiit.raidersreckoningapp.data.remote

import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.GetLeaderboardResponse
import `in`.iotkiit.raidersreckoningapp.data.util.Constants
import retrofit2.http.GET
import retrofit2.http.Header

interface LeaderboardApi {

    @GET(Constants.GET_LEADERBOARD_ENDPOINT)
    suspend fun getLeaderboard(
        @Header("Authorization") accessToken: String,
    ): CustomResponse<GetLeaderboardResponse>

}