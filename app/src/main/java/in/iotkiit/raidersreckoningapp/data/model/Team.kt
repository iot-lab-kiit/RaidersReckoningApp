package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("challenger")
    val isChallenger: Boolean,
    @SerializedName("leader")
    val leaderInfo: Participant,
    @SerializedName("points")
    val points: Int,
    @SerializedName("stats")
    val statsList: List<RoundStat>,
    @SerializedName("maxMembers")
    val maxMembers: Int,
    @SerializedName("participants")
    val participantsList: List<Participant>,
    @SerializedName("tempPoints")
    val tempPoints: List<Int>
)


