package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class GetTeamResponse(
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
    val statsList: List<Stats>,
    @SerializedName("maxMembers")
    val maxMembers: Int,
    val participants: List<Participant>,
    @SerializedName("tempPoints")
    val tempPoints: List<Int>,
    @SerializedName("is_leader")
    val isLeader: Boolean
)