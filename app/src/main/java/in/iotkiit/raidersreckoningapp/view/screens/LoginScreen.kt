package `in`.iotkiit.raidersreckoningapp.view.screens
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import `in`.iotkiit.nexterdayevents.view.components.login.GoogleOneTapButton
import `in`.iotkiit.raidersreckoningapp.BuildConfig
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.view.components.login.rememberFirebaseAuthLauncher
import `in`.iotkiit.raidersreckoningapp.view.navigation.RaidersReckoningScreens
import `in`.iotkiit.raidersreckoningapp.vm.DashBoardViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: DashBoardViewModel = hiltViewModel()
) {
    LoginScreenControl(
        onLoginSuccess = {
            navController.navigate(RaidersReckoningScreens.OnBoardingScreen.route) {
                popUpTo(RaidersReckoningScreens.LoginScreen.route) { inclusive = true }
            }
        },
        dashBoardViewModel = viewModel
    )
}

@Composable
private fun LoginScreenControl(
    onLoginSuccess: () -> Unit,
    dashBoardViewModel: DashBoardViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val verifyTokenState = dashBoardViewModel.verifyTokenState.collectAsState().value
    var resetTrigger by remember { mutableIntStateOf(0) }

    when (verifyTokenState) {
        is UiState.Idle -> {
            LoginScreenIdle(
                dashBoardViewModel = dashBoardViewModel,
                resetTrigger = resetTrigger,
                onResetTrigger = { resetTrigger++ }
            )
        }

        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            LaunchedEffect(Unit) {
                onLoginSuccess()
                dashBoardViewModel.resetVerifyTokenState()
            }
        }

        is UiState.Failed -> {
            LaunchedEffect(Unit) {
                Firebase.auth.signOut()
                Toast.makeText(context, "Login Failed! Please try again.", Toast.LENGTH_LONG).show()
                dashBoardViewModel.resetVerifyTokenState()
            }
        }
    }
}

@Composable
private fun LoginScreenIdle(
    dashBoardViewModel: DashBoardViewModel = hiltViewModel(),
    resetTrigger: Int,
    onResetTrigger: () -> Unit
) {
    val context = LocalContext.current
    val token = BuildConfig.FIREBASE_TOKEN

    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            result.user?.getIdToken(true)?.addOnSuccessListener { tokenResult ->
                val idToken = tokenResult.token
                if (idToken != null) {
                    Log.d("Google Auth", "Token: $idToken")
                    dashBoardViewModel.verifyToken(idToken)
                } else {
                    Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
                    onResetTrigger()
                }
            }?.addOnFailureListener {
                Toast.makeText(context, "Failed to fetch ID token!", Toast.LENGTH_LONG).show()
                onResetTrigger()
            }
        },
        onAuthError = { exception ->
            Log.e("Google Auth", "Authentication failed", exception)
            Toast.makeText(context, "Login Failed! Please try again.", Toast.LENGTH_LONG).show()
            onResetTrigger()
        }
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Image(
                painter = painterResource(R.drawable.title),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            GoogleOneTapButton {
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
    }
}