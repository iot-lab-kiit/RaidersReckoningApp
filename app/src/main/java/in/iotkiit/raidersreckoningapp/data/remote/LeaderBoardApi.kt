package `in`.iotkiit.raidersreckoningapp.data.remote

import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.LeaderBoard
import `in`.iotkiit.raidersreckoningapp.data.util.Constants
import retrofit2.http.GET
import retrofit2.http.Header

interface LeaderBoardApi {
        @GET(Constants.GET_LEADERBOARD_ENDPOINT)
        suspend fun getLeaderBoardItems(
            @Header("Authorization") accessToken: String,
        ): CustomResponse<LeaderBoard>
}