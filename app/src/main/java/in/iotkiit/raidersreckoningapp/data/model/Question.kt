package `in`.iotkiit.raidersreckoningapp.data.model

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("id")
    val id: String,
    @SerializedName("question")
    val question: String,
    @SerializedName("multiplier")
    val multiplier: Int,
    @SerializedName("timeAlloted")
    val timeAlloted: Int,
    @SerializedName("mcqAnswers")
    val mcqAnswers: List<String>,
    @SerializedName("correctAnswer")
    val correctAnswer: Int,
    @SerializedName("round")
    val round: Int,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("oneWord")
    val oneWord: Boolean,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
