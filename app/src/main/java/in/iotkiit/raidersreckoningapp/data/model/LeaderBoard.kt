package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class LeaderBoard (
    @SerializedName("leaderBoardItem")
    val leaderBoardItem: CustomResponse<List<LeaderBoardItem>>
)