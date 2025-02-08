package `in`.iotkiit.raidersreckoningapp.view.components.login

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import `in`.iotkiit.nexterdayevents.view.components.login.GoogleButtonTheme
import `in`.iotkiit.nexterdayevents.view.components.login.GoogleOneTapButton
import kotlin.math.roundToInt

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SwipeButton(
    modifier: Modifier = Modifier,
    onSwipeComplete: () -> Unit = {},
    resetTrigger: Int
) {
    val buttonHeight = 56.dp
    val swipeableState = rememberSwipeableState(0)

    LaunchedEffect(resetTrigger) {
        swipeableState.snapTo(0)
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth(0.7f)
            .height(buttonHeight)
    ) {
        val buttonWidth = maxWidth
        val maxSwipeDistance = buttonWidth - buttonHeight
        val maxSwipePx = with(LocalDensity.current) { maxSwipeDistance.toPx() }
        val anchors = mapOf(0f to 0, maxSwipePx to 1)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(2.dp, Color.White, RoundedCornerShape(buttonHeight / 2))
                .clip(RoundedCornerShape(buttonHeight / 2))
                .background(Color.Transparent)
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.6f) },
                    orientation = Orientation.Horizontal
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            val circleDiameterPx = with(LocalDensity.current) { buttonHeight.toPx() }
            val greenWidthPx = swipeableState.offset.value + circleDiameterPx / 2
            val greenWidthDp = with(LocalDensity.current) { greenWidthPx.toDp() }

            // Calculate the progress for color interpolation
            val progress = (greenWidthDp / buttonWidth).coerceIn(0f, 1f)

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(greenWidthDp.coerceAtMost(buttonWidth)),
                contentAlignment = Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Green)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Interpolate between white and black based on progress
                val textColor = lerp(Color.White, Color.Black, progress)
                Text(
                    text = "SIGN IN",
                    fontSize = 24.sp,
                    color = textColor,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Box(
                modifier = Modifier
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                    .size(buttonHeight)
                    .clip(CircleShape)
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                GoogleOneTapButton(
                    theme = GoogleButtonTheme.Dark,
                    iconOnly = true,
                    border = BorderStroke(2.dp, Color.White)
                )
            }
        }

        LaunchedEffect(swipeableState.currentValue) {
            if (swipeableState.currentValue == 1) {
                onSwipeComplete()
            }
        }
    }
}

private fun lerp(start: Color, end: Color, fraction: Float): Color {
    return Color(
        red = start.red + (end.red - start.red) * fraction,
        green = start.green + (end.green - start.green) * fraction,
        blue = start.blue + (end.blue - start.blue) * fraction,
        alpha = start.alpha + (end.alpha - start.alpha) * fraction
    )
}