package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class Zone(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("teams")
    val teams: List<Team>,
    @SerializedName("venue")
    val venue: String,
    @SerializedName("clockStarted")
    val clockStarted: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("startTime")
    val startTime: Long,
    @SerializedName("endTime")
    val endTime: Long,
    @SerializedName("duration")
    val duration: Long
)