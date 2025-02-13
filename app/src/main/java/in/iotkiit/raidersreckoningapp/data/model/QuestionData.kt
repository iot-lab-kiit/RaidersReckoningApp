package `in`.iotkiit.raidersreckoningapp.data.model

data class QuestionData(
    val questions: List<Question>,
    val zoneStartTime: Long,
    val zoneDuration: Long,
    val zoneEndTime: Long
)
