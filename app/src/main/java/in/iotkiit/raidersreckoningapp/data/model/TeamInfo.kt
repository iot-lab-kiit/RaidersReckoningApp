package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class TeamInfo(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)
