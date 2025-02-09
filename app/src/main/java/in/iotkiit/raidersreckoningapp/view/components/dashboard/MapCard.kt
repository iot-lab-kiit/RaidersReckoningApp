package `in`.iotkiit.raidersreckoningapp.view.components.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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
    Surface(modifier = modifier.clip(RoundedCornerShape(16.dp))) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
        ) {

            Image(
                painter = mapImage,
                contentDescription = "Map",

                )

            Image(
                painter = soldierImage,
                contentDescription = "Soldier",
                modifier = Modifier
                    .size(soldierSize.dp)
            )
        }
    }
}

