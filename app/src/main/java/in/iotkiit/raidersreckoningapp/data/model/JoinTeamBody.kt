package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class JoinTeamBody(
    @SerializedName("teamId")
    val teamId: String
)