package `in`.iotkiit.raidersreckoningapp.view.components.myTeam

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD

@Composable
fun Fields(
    field: String,
    copy: Float
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .background(
                color = GreenCOD.copy(copy),
                shape = RoundedCornerShape(15.dp)
            )
            .border(
                width = 1.04.dp,
                color = GreenCOD,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(20.dp)
    ) {
        Text(
            text = field,
            style = MaterialTheme.typography.headlineMedium,
            color = GreenCOD
        )
    }
}

@Preview
@Composable
private fun FieldsPrev() {
    Fields("Kunal", 0.1f)
}