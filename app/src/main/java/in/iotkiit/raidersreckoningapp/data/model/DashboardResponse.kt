package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class DashboardResponse(
    @SerializedName("team")
    val team: Team,
    @SerializedName("zone")
    val zone: Zone
)
