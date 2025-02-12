package `in`.iotkiit.raidersreckoningapp.view.components.core

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TeamCard(
    modifier: Modifier = Modifier,
    teamName: String,
    leaderName: String,
    teamMembers: List<String>
) {
    OutlinedCard(
        modifier = modifier.padding(16.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color.Black,
            contentColor = Color(0xFF00FF00)
        ),
        border = BorderStroke(3.dp, Color(0xFF00FF00))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Team Name Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF00FF00))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = teamName.uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp
                )
            }

            val allMembers = listOf(leaderName) + teamMembers // Ensure leader is first

            // Team Members
            allMembers.forEachIndexed { index, member ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = member.uppercase(),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00FF00),
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f)
                    )

                    // Show icon for leader
                    if (index == 0) {
                        Icon(
                            imageVector = Icons.Default.Star, // You can replace with any Material icon
                            contentDescription = "Team Leader",
                            tint = Color(0xFF00FF00),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                // Divider between members
                if (index != allMembers.lastIndex) {
                    HorizontalDivider(
                        color = Color(0xFF00FF00),
                        thickness = 3.dp,
                    )
                }
            }
        }
    }
}



@Preview
@Composable
fun TeamDetailsCardPreview() {
    val teamName = "TaskForce141"
    val teamMembers = listOf(
        "Soap",
        "Price",
        "Gaz"
    )
    TeamCard(
        teamName = teamName,
        teamMembers = teamMembers,
        leaderName = "Ghost"
    )
}

