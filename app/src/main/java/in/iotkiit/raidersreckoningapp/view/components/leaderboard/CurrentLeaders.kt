package `in`.iotkiit.raidersreckoningapp.view.components.leaderboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iotkiit.raidersreckoningapp.R

@Composable
fun CurrentLeaders(
    modifier: Modifier = Modifier,
    players: String
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

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = players.getOrElse(0) { "Player 1" },
                    style = TextStyle(
                        color = Color.White,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = players.getOrElse(1) { "Player 2" },
                    style = TextStyle(color = Color.White, fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = players.getOrElse(2) { "Player 3" },
                    style = TextStyle(color = Color.White, fontSize = 16.sp),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }
}