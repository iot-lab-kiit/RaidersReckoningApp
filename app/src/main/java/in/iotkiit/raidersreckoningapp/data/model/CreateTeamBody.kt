package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class CreateTeamBody(
    @SerializedName("name")
    val name: String
)