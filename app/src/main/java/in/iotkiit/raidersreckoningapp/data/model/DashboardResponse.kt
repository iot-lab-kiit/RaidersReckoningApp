package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class DashboardResponse(
    @SerializedName("teamName")
    val teamName: String,
    @SerializedName("points")
    val points: Int,
    @SerializedName("zone")
    val zone: Zone,
    @SerializedName("round")
    val round: Int,
    @SerializedName("isChallenger")
    val isChallenger: Boolean,
    @SerializedName("paused")
    val paused: Boolean,
)
