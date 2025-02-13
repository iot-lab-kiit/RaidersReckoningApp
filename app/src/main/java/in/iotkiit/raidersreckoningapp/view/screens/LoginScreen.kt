package `in`.iotkiit.raidersreckoningapp.view.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import `in`.iotkiit.raidersreckoningapp.view.components.login.GoogleOneTapButton
import `in`.iotkiit.raidersreckoningapp.BuildConfig
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.view.components.login.GoogleButtonTheme
import `in`.iotkiit.raidersreckoningapp.view.components.login.rememberFirebaseAuthLauncher
import `in`.iotkiit.raidersreckoningapp.view.navigation.RaidersReckoningScreens
import `in`.iotkiit.raidersreckoningapp.vm.DashBoardViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: DashBoardViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val verifyTokenState = viewModel.verifyTokenState.collectAsState().value
    var resetTrigger by remember { mutableIntStateOf(0) }
    val token = BuildConfig.FIREBASE_TOKEN

    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            result.user?.getIdToken(true)?.addOnSuccessListener { tokenResult ->
                val idToken = tokenResult.token
                if (idToken != null) {
                    Log.d("Google Auth", "Token: $idToken")
                    viewModel.verifyToken(idToken)
                } else {
                    Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
                    resetTrigger++
                }
            }?.addOnFailureListener {
                Toast.makeText(context, "Failed to fetch ID token!", Toast.LENGTH_LONG).show()
                resetTrigger++
            }
        },
        onAuthError = { exception ->
            Log.e("Google Auth", "Authentication failed", exception)
            Toast.makeText(context, "Login Failed! Please try again.", Toast.LENGTH_LONG).show()
            resetTrigger++
        }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Background and main content
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(400.dp, Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(R.drawable.title),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            GoogleOneTapButton(
                theme = GoogleButtonTheme.Dark
            ) {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(token)
                    .requestEmail()
                    .build()

                val googleSignInClient = GoogleSignIn.getClient(context, gso)

                googleSignInClient.signOut().addOnCompleteListener {
                    googleSignInClient.revokeAccess().addOnCompleteListener {
                        Log.d("LoginScreen", "Signed out and access revoked.")
                        launcher.launch(googleSignInClient.signInIntent)
                    }
                }
            }
        }

        // Loading overlay
        if (verifyTokenState is UiState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }
    }

    // Handle success and failure states
    LaunchedEffect(verifyTokenState) {
        when (verifyTokenState) {
            is UiState.Success -> {
                navController.navigate(RaidersReckoningScreens.OnBoardingScreen.route) {
                    popUpTo(RaidersReckoningScreens.LoginScreen.route) { inclusive = true }
                }
                viewModel.resetVerifyTokenState()
            }

            is UiState.Failed -> {
                Firebase.auth.signOut()
                Toast.makeText(context, "Login Failed! Please try again.", Toast.LENGTH_LONG).show()
                viewModel.resetVerifyTokenState()
            }

            else -> {}
        }
    }
}