package `in`.iotkiit.raidersreckoningapp.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.view.navigation.RaidersReckoningScreens
import `in`.iotkiit.raidersreckoningapp.vm.TeamViewModel
import com.google.firebase.auth.FirebaseAuth
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD

@Composable
fun SplashScreen(
    navController: NavController,
    teamViewModel: TeamViewModel = hiltViewModel()
) {
    val getTeamState = teamViewModel.getTeamState.collectAsState().value
    val currentUser = FirebaseAuth.getInstance().currentUser

    // Main content
    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = "Splash Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Logo in center
        Image(
            painter = painterResource(R.drawable.title),
            contentDescription = "App Logo",
            modifier = Modifier.align(Alignment.Center),
            contentScale = ContentScale.FillWidth
        )

        // Loading indicator
        if (getTeamState is UiState.Loading) {
            LinearProgressIndicator(
                color = GreenCOD,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    LaunchedEffect(currentUser, getTeamState) {
        if (currentUser == null) {
            navController.navigate(RaidersReckoningScreens.LoginScreen.route) {
                popUpTo(RaidersReckoningScreens.SplashScreen.route) { inclusive = true }
            }
        }

        when (getTeamState) {
            is UiState.Idle -> {
                if (currentUser != null) {
                    teamViewModel.getTeam()
                }
            }
            is UiState.Success -> {
                val destination = if (getTeamState.data.data == null) {
                    RaidersReckoningScreens.OnBoardingScreen.route
                } else {
                    RaidersReckoningScreens.DashBoardScreen.route
                }
                navController.navigate(destination) {
                    popUpTo(RaidersReckoningScreens.SplashScreen.route) { inclusive = true }
                }
            }
            is UiState.Failed -> {
                // If team fetch fails, assume user needs to login again
                FirebaseAuth.getInstance().signOut()
                navController.navigate(RaidersReckoningScreens.LoginScreen.route) {
                    popUpTo(RaidersReckoningScreens.SplashScreen.route) { inclusive = true }
                }
            }

            else -> {}
        }
    }
}