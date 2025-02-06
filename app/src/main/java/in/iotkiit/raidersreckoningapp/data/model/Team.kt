package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("challenger")
    val challenger: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("leader")
    val leader: Participant,
    @SerializedName("name")
    val name: String,
    @SerializedName("participants")
    val participants: List<Participant>,
    @SerializedName("points")
    val points: String,
    @SerializedName("stats")
    val roundStats: List<RoundStat>
)


