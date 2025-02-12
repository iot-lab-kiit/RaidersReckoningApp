package `in`.iotkiit.raidersreckoningapp.view.components.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MapCard(
    modifier: Modifier = Modifier,
    mapImage: Painter,
    soldierImage: Painter,
    soldierSize: Dp = 256.dp,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd,
    ) {

        Image(
            painter = mapImage,
            contentDescription = "Map",
            modifier = Modifier.clip(RoundedCornerShape(20.dp)),
            contentScale = ContentScale.Fit
        )

        Image(
            painter = soldierImage,
            contentDescription = "Soldier",
            modifier = Modifier
                .size(soldierSize)
                .align(Alignment.BottomEnd)
        )
    }
}
