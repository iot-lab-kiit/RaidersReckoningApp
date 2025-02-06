package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class Zone(
    @SerializedName("clock")
    val clockStarted: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("teams")
    val teams: List<TeamInfo>
)