package `in`.iotkiit.raidersreckoningapp.view.components.myTeam

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD

@Composable
fun Fields(
    field: String,
    containerColor: Color = GreenCOD.copy(alpha = 0.1f),
    contentColor: Color = GreenCOD,
    borderColor: Color = GreenCOD,
    borderWidth: Dp = 1.dp,
    maxLines: Int = Int.MAX_VALUE
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = containerColor,
                shape = RoundedCornerShape(15.dp)
            )
            .border(
                width = borderWidth,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Text(
            text = field,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = when {
                    field.length > 200 -> 18.sp
                    field.length > 100 -> 20.sp
                    else -> 24.sp
                }
            ),
            color = contentColor,
            maxLines = maxLines,
            softWrap = true,
            modifier = Modifier.weight(1f, fill = false)
        )
    }
}

@Preview
@Composable
private fun FieldsPrev() {
    Fields("Kunal")
}