package `in`.iotkiit.raidersreckoningapp.view.screens

import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.view.navigation.RaidersReckoningScreens
import `in`.iotkiit.raidersreckoningapp.vm.TeamViewModel

@OptIn(UnstableApi::class)
@Composable
fun SplashScreen(
    navController: NavController,
    teamViewModel: TeamViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val videoUri = RawResourceDataSource.buildRawResourceUri(R.raw.splash_screen)
            val mediaItem = MediaItem.fromUri(videoUri)
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    val getTeamState = teamViewModel.getTeamState.collectAsState().value
    val isVideoCompleted = remember { mutableStateOf(false) }
    val isNavigating = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        teamViewModel.getTeam()
    }

    LaunchedEffect(exoPlayer) {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    isVideoCompleted.value = true
                }
            }
        })
    }

    LaunchedEffect(getTeamState, isVideoCompleted.value) {
        if (isVideoCompleted.value && getTeamState !is UiState.Loading && !isNavigating.value) {
            isNavigating.value = true
            when (getTeamState) {
                is UiState.Success -> {
                    if (Firebase.auth.currentUser != null) {
                        navController.navigate(RaidersReckoningScreens.DashBoardScreen.route) {
                            popUpTo("splash_screen") { inclusive = true }
                        }
                    } else {
                        navController.navigate(RaidersReckoningScreens.LoginScreen.route) {
                            popUpTo("splash_screen") { inclusive = true }
                        }
                    }
                }
                is UiState.Failed -> {
                    navController.navigate(RaidersReckoningScreens.LoginScreen.route) {
                        popUpTo("splash_screen") { inclusive = true }
                    }
                }
                else -> {
                    isNavigating.value = false
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            if (!isNavigating.value) {
                exoPlayer.release()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}