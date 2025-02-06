package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class DashboardData(
    @SerializedName("team")
    val team: DashBoardTeamResponse,
    @SerializedName("zone")
    val zone: Zone
)
