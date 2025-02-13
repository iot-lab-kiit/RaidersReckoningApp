package `in`.iotkiit.raidersreckoningapp.view.components.myTeam

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.qr_generator_compose.qrGenerator
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD

@Composable
fun ExpandableQRCard(teamId: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth(0.69f)
            .padding(16.dp)
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = GreenCOD.copy(0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (expanded) "Hide QR Code" else "Show QR Code",
                color = GreenCOD,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Image(
                    painter = qrGenerator(
                        content = teamId,
                        size = 200.dp
                    ),
                    contentDescription = "Team QR Code",
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}