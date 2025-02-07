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
    val timeAlloted: Number,
    @SerializedName("mcqAnswers")
    val mcqAnswers: List<String>,
    @SerializedName("correctAnswer")
    val correctAnswer: Number,
    @SerializedName("round")
    val round: Number,
    @SerializedName("duration")
    val duration: Number,
    @SerializedName("oneWord")
    val oneWord: Boolean,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)
