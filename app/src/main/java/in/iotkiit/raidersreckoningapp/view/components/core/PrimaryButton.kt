package `in`.iotkiit.raidersreckoningapp.view.components.core

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.ui.theme.modernWarfare

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    shape: Shape = RoundedCornerShape(50),
    borderWidth: Dp = 2.dp,
    contentColor: Color = Color.Black,
    containerColor: Color = GreenCOD,
) {
    Button(
        onClick = { onClick() },
        shape = shape,
        modifier = modifier
            .fillMaxWidth(0.7f)
            .border(
                borderWidth,
                Color.Green,
                RoundedCornerShape(50)
            ),
        colors = ButtonDefaults.primaryButtonColors(containerColor)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(4.dp),
            color = contentColor,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
