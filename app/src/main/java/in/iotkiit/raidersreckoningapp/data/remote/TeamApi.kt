package `in`.iotkiit.raidersreckoningapp.data.remote

import `in`.iotkiit.raidersreckoningapp.data.model.CreateTeamBody
import `in`.iotkiit.raidersreckoningapp.data.model.CustomResponse
import `in`.iotkiit.raidersreckoningapp.data.model.JoinTeamBody
import `in`.iotkiit.raidersreckoningapp.data.util.Constants
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TeamApi {
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

}