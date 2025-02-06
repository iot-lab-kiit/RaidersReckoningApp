package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class RoundStat(
    @SerializedName("endTime")
    val endTime: String,
    @SerializedName("points")
    val points: String,
    @SerializedName("round")
    val round: String,
    @SerializedName("startTime")
    val startTime: String,
    @SerializedName("winner")
    val winner: String
)