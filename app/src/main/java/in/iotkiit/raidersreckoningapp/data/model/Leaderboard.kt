package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class Leaderboard (
    @SerializedName("team")
    val team: Team,
    @SerializedName("points")
    val points: Int
)