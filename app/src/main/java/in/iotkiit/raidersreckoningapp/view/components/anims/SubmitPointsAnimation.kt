package `in`.iotkiit.raidersreckoningapp.view.components.anims

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.ui.theme.RaidersReckoningAppTheme


// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    RaidersReckoningAppTheme {
        SubmitPointsAnimation()
    }
}


/**
 * This composable function is used to show the review posted animation when any review is posted.
 */
@Composable
fun SubmitPointsAnimation(
    modifier: Modifier = Modifier,
    onAnimationComplete: (() -> Unit)? = null
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.submit_score_anim))
    val progress by animateLottieCompositionAsState(composition)

    // This variable says if the dialog is Visible or not
    var isVisible by remember { mutableStateOf(true) }

    onAnimationComplete?.let {
        LaunchedEffect(progress) {
            if (progress == 1f) {
                isVisible = false
                it()
            }
        }
    }


    AnimationDialog(
        modifier = modifier,
        isVisible = isVisible
    ) {

        LottieAnimation(
            composition = composition,
            modifier = Modifier.size(160.dp),
            speed = 1.5f
        )

        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + expandVertically()
        ) {
            Text(
                text = "Round Ended!\nGet Ready For Next Round!",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}