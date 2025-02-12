package `in`.iotkiit.raidersreckoningapp.data.remote

import `in`.iotkiit.raidersreckoningapp.data.model.CreateTeamBody
import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.GetTeamResponse
import `in`.iotkiit.raidersreckoningapp.data.model.JoinTeamBody
import `in`.iotkiit.raidersreckoningapp.data.model.Question
import `in`.iotkiit.raidersreckoningapp.data.model.QuestionData
import `in`.iotkiit.raidersreckoningapp.data.model.SubmitPointsBody
import `in`.iotkiit.raidersreckoningapp.data.model.TeamInfo
import `in`.iotkiit.raidersreckoningapp.data.util.Constants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TeamApi {

    @GET(Constants.VERIFY_TOKEN_ENDPOINT)
    suspend fun verifyToken(
        @Header("Authorization") accessToken: String
    ): CustomResponse<Unit>

    @POST(Constants.CREATE_TEAM_ENDPOINT)
    suspend fun createTeam(
        @Header("Authorization") accessToken: String,
        @Body createTeamBody: CreateTeamBody
    ): CustomResponse<Unit>

    @POST(Constants.JOIN_TEAM_ENDPOINT)
    suspend fun joinTeam(
        @Header("Authorization") accessToken: String,
        @Body joinTeamBody: JoinTeamBody
    ): CustomResponse<Unit>

    @GET(Constants.GET_TEAM_ENDPOINT)
    suspend fun getTeam(
        @Header("Authorization") accessToken: String
    ): CustomResponse<GetTeamResponse>

    @GET(Constants.GET_QUESTIONS)
    suspend fun getQuestions(
        @Header("Authorization") accessToken: String,
        @Query("zoneId") zoneId: String
    ): CustomResponse<QuestionData>

    @POST("api/team/set_temp_points")
    suspend fun submitPoints(
        @Header("Authorization") accessToken: String,
        @Body submitPointsBody: SubmitPointsBody
    ): CustomResponse<Unit>
}