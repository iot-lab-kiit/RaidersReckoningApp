package `in`.iotkiit.raidersreckoningapp.view.components.core

import androidx.compose.ui.graphics.Color
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD

data class ProgressBarTheme(
    val background: Color,
    val border: Color,
    val borderHighlight: Color,
    val progress: Color,
    val progressHighlight: Color,
)

val GreenProgressTheme = ProgressBarTheme(
    background = GreenCOD.copy(alpha = 0.1f),
    border = GreenCOD.copy(alpha = 0.5f),
    borderHighlight = GreenCOD.copy(alpha = 0.7f),
    progress = GreenCOD.copy(alpha = 0.5f),
    progressHighlight = Color.White
)
