package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class LeaderboardData(
    @SerializedName("leaderboard")
    val leaderboard: List<LeaderboardEntry>
)