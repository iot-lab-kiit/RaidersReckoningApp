package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class DashboardTeams(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)
