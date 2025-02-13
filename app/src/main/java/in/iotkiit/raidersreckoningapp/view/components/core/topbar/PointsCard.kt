package `in`.iotkiit.raidersreckoningapp.view.components.core.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.ui.theme.modernWarfare

@Composable
fun PointsCard(
    modifier: Modifier = Modifier,
    points: Int?
) {
    Card(
        modifier = modifier
            .clip(RectangleShape)
            .padding(end = 8.dp),
        colors = CardColors(
            contentColor = Color.Black,
            containerColor = GreenCOD,
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified
        )
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = points.toString(),
                maxLines = 1,
                fontFamily = modernWarfare,
                fontSize = 12.sp,
                color = Color.Black
            )

            Image(
                painter = painterResource(R.drawable.points),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PointsCardPrev() {
    PointsCard(modifier = Modifier ,100)
}