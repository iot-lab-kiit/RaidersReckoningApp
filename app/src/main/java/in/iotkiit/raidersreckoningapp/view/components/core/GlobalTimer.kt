package `in`.iotkiit.raidersreckoningapp.view.components.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun useGlobalTimer(
    zoneStartTime: Long?,
    zoneDuration: Long
): Triple<Int, Int, Boolean> {
    var remainingTimeInSeconds by remember {
        mutableIntStateOf(calculateRemainingTime(zoneStartTime, zoneDuration))
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            remainingTimeInSeconds = calculateRemainingTime(zoneStartTime, zoneDuration)
        }
    }

    val minutes = remainingTimeInSeconds / 60
    val seconds = remainingTimeInSeconds % 60
    val isTimeUp = remainingTimeInSeconds <= 0

    return Triple(minutes, seconds, isTimeUp)
}

private fun calculateRemainingTime(zoneStartTime: Long?, zoneDuration: Long): Int {
    return if (zoneStartTime == null) {
        (zoneDuration / 1000).toInt()
    } else {
        val currentTimeInMillis = System.currentTimeMillis()
        val endTime = (zoneStartTime + zoneDuration) - currentTimeInMillis
        val endTimeInSeconds = endTime/1000
        (endTimeInSeconds).toInt().coerceAtLeast(0)
    }
}