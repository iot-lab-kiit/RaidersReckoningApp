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
import `in`.iotkiit.raidersreckoningapp.ui.theme.modernWarfare

@Composable
fun PointsCard(
    modifier: Modifier = Modifier,
    points: Int
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardColors(
            contentColor = Color.Black,
            containerColor = Color.Green,
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = points.toString(),
                fontFamily = modernWarfare,
                fontSize = 20.sp
            )
            Image(
                painter = painterResource(R.drawable.points),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

        }
    }
}