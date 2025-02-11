package `in`.iotkiit.raidersreckoningapp.data.model

data class Stats(
    val teamId: String,
    val points: Int,
    val round: Int,
    val startTime: Int,
    val endTime: Int,
    val winner: Boolean
)
