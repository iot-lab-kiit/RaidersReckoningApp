package `in`.iotkiit.raidersreckoningapp.view.components.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun MapCard(
    modifier: Modifier = Modifier,
    mapImage: Painter,
    soldierImage: Painter,
    soldierSize: Int = 256,
) {
    Row {
        Box(
            modifier = modifier.clip(RoundedCornerShape(18.dp)),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                painter = mapImage,
                contentDescription = "Map",
                modifier = Modifier
            )

            Image(
                painter = soldierImage,
                contentDescription = "Soldier",
                modifier = Modifier.size(soldierSize.dp)
            )
        }
    }
}

