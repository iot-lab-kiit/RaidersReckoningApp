package `in`.iotkiit.raidersreckoningapp.view.components.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp


//TODO fixes required
@Composable
fun MapCard(
    modifier: Modifier = Modifier,
    mapImage: Painter,
    soldierImage: Painter,
) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.BottomEnd
    ) {

        Image(
            painter = mapImage,
            contentDescription = null,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(16.dp))
        )

        Image(
            painter = soldierImage,
            contentDescription = null
        )

    }
}