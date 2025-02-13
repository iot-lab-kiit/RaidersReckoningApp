package `in`.iotkiit.raidersreckoningapp.view.components.anims

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.ui.theme.RaidersReckoningAppTheme
import `in`.iotkiit.raidersreckoningapp.view.components.core.PrimaryButton


// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview3() {
    RaidersReckoningAppTheme {
        FailureAnimationDialog {}
    }
}

/**
 * This composable function is used to show an internet error screen with an animation.
 */
@Composable
fun FailureAnimationDialog(
    modifier: Modifier = Modifier,
    message: String = "No internet connection. Please try again later.",
    onAnimationComplete: (() -> Unit)? = null,
    onTryAgainClick: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.internet_error))
    val progress by animateLottieCompositionAsState(composition)


    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        LottieAnimation(
            composition = composition,
            modifier = Modifier.size(200.dp)
        )


        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        PrimaryButton(
            onClick = onTryAgainClick,
            text = "Try Again",
            modifier = Modifier.fillMaxWidth()
        )
    }

    onAnimationComplete?.let {
        if (progress == 1.0f) it()
    }
}
