package `in`.iotkiit.raidersreckoningapp.view.components.questions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.ui.theme.modernWarfare

@Composable
fun CircularTimer(
    currentTime: Int,
    totalTime: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .background(Color.Black, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        // Background circle
        CircularProgressIndicator(
            progress = 1f,
            modifier = Modifier.size(60.dp),
            color = Color.DarkGray,
            strokeWidth = 4.dp
        )

        // Progress circle
        CircularProgressIndicator(
            progress = currentTime.toFloat() / totalTime,
            modifier = Modifier.size(60.dp),
            color = GreenCOD,
            strokeWidth = 4.dp
        )

        // Center text
        Text(
            text = currentTime.toString(),
            color = GreenCOD,
            fontSize = 20.sp,
            fontFamily = modernWarfare
        )
    }
}