package `in`.iotkiit.raidersreckoningapp.view.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import `in`.iotkiit.raidersreckoningapp.BuildConfig
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.view.components.login.GoogleOneTapButton
import `in`.iotkiit.raidersreckoningapp.view.components.login.rememberFirebaseAuthLauncher
import `in`.iotkiit.raidersreckoningapp.vm.DashBoardViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    dashBoardViewModel: DashBoardViewModel = hiltViewModel()
) {
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val context = LocalContext.current
    val token = BuildConfig.FIREBASE_TOKEN
    val verifyTokenState = dashBoardViewModel.verifyTokenState.collectAsState().value
    var navigated by remember { mutableStateOf(false) }

    val launcher = rememberFirebaseAuthLauncher(onAuthComplete = { result ->
        user = result.user
        val userCurr = FirebaseAuth.getInstance().currentUser
        if (userCurr != null) {
            userCurr.getIdToken(true)
                .addOnSuccessListener { tokenResult ->
                    val idToken = tokenResult.token
                    if (idToken != null) {
                        Log.d("Google Auth", "Token: $idToken")
                        dashBoardViewModel.verifyToken(idToken)
                    } else {
                        Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to fetch ID token!", Toast.LENGTH_LONG).show()
                }
        }
    }, onAuthError = {
        user = null
        Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
    })

    when (verifyTokenState) {
        is UiState.Loading -> {
            Unit
        }

        is UiState.Success -> {
            LaunchedEffect(verifyTokenState) {
                if (!navigated) {
                    navigated = true
                    onLoginSuccess()
                    dashBoardViewModel.resetVerifyTokenState()
                }
            }
        }

        is UiState.Failed -> {
            Firebase.auth.signOut()
            Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
        }

        else -> Unit
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceContainerLowest
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome Onboard!",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Column(
                modifier = Modifier
                    .weight(0.3f)
            ) {
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

                if (verifyTokenState is UiState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}