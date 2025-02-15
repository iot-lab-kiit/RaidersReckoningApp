package `in`.iotkiit.raidersreckoningapp.view.components.questions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD

@Composable
fun ClickableFields(
    field: String,
    containerColor: Color = GreenCOD.copy(alpha = 0.1f),
    contentColor: Color = GreenCOD,
    borderColor: Color = GreenCOD,
    borderWidth: Dp = 1.dp,
    onClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .clickable(onClick = onClick)
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
                    field.length > 200 -> 10.sp
                    field.length > 100 -> 12.sp
                    else -> 14.sp
                }
            ),
            color = contentColor,
            softWrap = true,
            modifier = Modifier
                .weight(1f, fill = false)
        )
    }
}

@Preview
@Composable
private fun ClickableFieldsPreview() {
    ClickableFields(
        field = "This is a very long text that needs to be wrapped properly and " +
                "should still look good in the clickable field while maintaining readability " +
                "and proper layout structure.",
        onClick = {}
    )
}