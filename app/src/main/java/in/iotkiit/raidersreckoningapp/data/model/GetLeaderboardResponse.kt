package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class GetLeaderboardResponse(
    @SerializedName("leaderboard")
    val leaderboard: List<Leaderboard>,
    @SerializedName("teamName")
    val teamName: String,
    @SerializedName("teamPoints")
    val points: Int
)