package `in`.iotkiit.raidersreckoningapp.view.components.leaderboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iotkiit.raidersreckoningapp.R

@Composable
fun CurrentLeaders(
    modifier: Modifier = Modifier,
    players: List<String>
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.leaderboard),
            contentDescription = "Leaderboard background",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )

        val rank = listOf(1, 0, 2)

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            rank.forEach { position ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    val playerName = players.getOrElse(position) { "Player ${position+1} " }
                    val shortName =
                        if (playerName.length > 16) playerName.take(12) + "..." else playerName
                    Text(
                        text = shortName,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .offset(y = if (position == 0) (-40).dp else 0.dp),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}