package `in`.iotkiit.raidersreckoningapp.view.components.core.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.ui.theme.modernWarfare

@Composable
fun TeamNameCard(
    modifier: Modifier = Modifier,
    teamName: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardColors(
            contentColor = GreenCOD,
            containerColor = GreenCOD.copy(alpha = 0.1f),
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ghost_icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Text(
                modifier= Modifier.padding(4.dp),
                fontFamily = modernWarfare,
                text = teamName,
                fontSize = 24.sp
            )
        }
    }
}
