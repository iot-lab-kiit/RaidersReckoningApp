package `in`.iotkiit.raidersreckoningapp.view.components.anims

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD
import `in`.iotkiit.raidersreckoningapp.view.components.core.GreenProgressTheme


// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    LoadingTransition(
        modifier = Modifier.fillMaxWidth(),
        textDurationMillis = 1000
    )
}


/**
 * This is the Loading Animation to be used in the Whole App.
 *
 * @param modifier This is the optional [Modifier] which can be passed from the Parent.
 * @param textDurationMillis This is the Time according to which texts shall switch and loads.
 */
@Composable
fun LoadingTransition(
    modifier: Modifier = Modifier,
    textDurationMillis: Int = 1000
) {

    // List of Texts to show the User
    val list = remember {
        listOf(
            "Reloading Magazines", "Locking Targets", "Scanning Battlefields",
            "Equipping Armor", "Sharpening Sights", "Securing Objectives",
            "Deploying UAV", "Calling in Killstreaks", "Engaging Hostiles",
            "Cracking Codes", "Solving Riddles", "Analyzing Patterns",
            "Connecting Dots"
        ).shuffled()
    }

    // Transition Type is infinite
    val transition = rememberInfiniteTransition("Loading Screen Transition")

    // This keeps the Progress Bar float data
    var progress by remember { mutableFloatStateOf(0f) }

    // This determines which text among the two ticking shall be shown
    var sudo by remember { mutableStateOf(false) }

    // First and Second Text Index value
    var indexA by remember { mutableIntStateOf(0) }
    var indexB by remember { mutableIntStateOf(0) }

    // This index value changes every given time and triggers the Text changing Animation
    val index by transition.animateFloat(
        initialValue = 0f,
        targetValue = list.size.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = textDurationMillis * list.size,
                easing = LinearEasing
            )
        ),
        label = "Text"
    )

    // Progress calculator to slow the progress with time
    progress += (1f - progress) * 0.045f + 0f * index

    // Changing state according to the index value generated each trigger
    LaunchedEffect(index.toInt()) {
        sudo = !sudo
        if (sudo)
            indexA = index.toInt() % list.size
        else
            indexB = index.toInt() % list.size
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {


        // Progress Bar
        ThemedProgressBar(
            progress = progress,
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            colorScheme = GreenProgressTheme
        )

        // Box which shows the Texts
        Box(modifier.padding(24.dp).fillMaxWidth()) {

            // First Text Field
            androidx.compose.animation.AnimatedVisibility(
                modifier = modifier.fillMaxWidth(),
                visible = sudo,
                enter = slideIn(initialOffset = { IntOffset(0, -it.height) }) + fadeIn(),
                exit = slideOut(targetOffset = { IntOffset(0, it.height / 2) }) + fadeOut()
            ) {
                Text(
                    text = list[indexA],
                    style = MaterialTheme.typography.bodyMedium,
                    color = GreenCOD,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Second Text Field
            androidx.compose.animation.AnimatedVisibility(
                modifier = modifier.fillMaxWidth(),
                visible = !sudo,
                enter = slideIn(initialOffset = { IntOffset(0, -it.height) }) + fadeIn(),
                exit = slideOut(targetOffset = { IntOffset(0, it.height / 2) }) + fadeOut()
            ) {
                Text(
                    text = list[indexB],
                    style = MaterialTheme.typography.bodyMedium,
                    color = GreenCOD,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}