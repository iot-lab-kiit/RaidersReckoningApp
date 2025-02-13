package `in`.iotkiit.raidersreckoningapp.view.components.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD

@Composable
fun LeaderboardFields(
    teamName: String,
    rank: String,
    points: String
) {
    Column(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = GreenCOD.copy(.1f),
                    shape = RoundedCornerShape(15.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                text = rank,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Text(
                text = teamName,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Text(
                text = points,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
        }
    }
}