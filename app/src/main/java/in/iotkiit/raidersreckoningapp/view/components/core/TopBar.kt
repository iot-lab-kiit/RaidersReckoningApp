package `in`.iotkiit.raidersreckoningapp.view.components.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.ui.theme.modernWarfare


@Preview
@Composable
private fun TopBarPreview() {
    TopBar(
        teamName = "TaskForce141",
        points = 10
    )
}


//TODO fixes required
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    teamName: String,
    points: Int
) {
    TopAppBar(
        modifier = modifier,
        title = {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamNameCard(teamName = teamName)
                PointsCard(points = points)
            }
        }

    )
}



@Composable
fun TeamNameCard(
    modifier: Modifier = Modifier,
    teamName: String
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardColors(
            contentColor = Color.Green,
            containerColor = Color.Black,
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ghost_icon),
                contentDescription = null
            )

            Text(
                fontFamily = modernWarfare,
                text = teamName
            )
        }
    }
}



@Composable
fun PointsCard(
    modifier: Modifier = Modifier,
    points: Int
) {
    Card(
        modifier = modifier,
        colors = CardColors(
            contentColor = Color.Black,
            containerColor = Color.Green,
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified
        )
    ) {
        Row {
            Text(text = points.toString(),)
            Image(
                painter = painterResource(R.drawable.points),
                contentDescription = null
            )

        }
    }
}