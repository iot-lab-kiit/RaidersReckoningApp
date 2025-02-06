package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class LeaderBoardItem(
    @SerializedName("team")
    val team: Team
)