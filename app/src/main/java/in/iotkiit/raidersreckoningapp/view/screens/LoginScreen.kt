package `in`.iotkiit.raidersreckoningapp.view.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import `in`.iotkiit.raidersreckoningapp.BuildConfig
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.view.components.login.SwipeButton
import `in`.iotkiit.raidersreckoningapp.view.components.login.rememberFirebaseAuthLauncher
import `in`.iotkiit.raidersreckoningapp.vm.DashBoardViewModel

@Composable
fun LoginScreenControl(
    onLoginSuccess: () -> Unit,
    dashBoardViewModel: DashBoardViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val verifyTokenState = dashBoardViewModel.verifyTokenState.collectAsState().value
    var navigated by remember { mutableStateOf(false) }
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
            LaunchedEffect(verifyTokenState) {
                if (!navigated) {
                    navigated = true
                    onLoginSuccess()
                    dashBoardViewModel.resetVerifyTokenState()
                }
            }
        }

        is UiState.Failed -> {
            LaunchedEffect(verifyTokenState) {
                Firebase.auth.signOut()
                Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
                resetTrigger++
                dashBoardViewModel.resetVerifyTokenState()
            }
        }

    }
}

@Composable
private fun LoginScreenIdle(
    dashBoardViewModel: DashBoardViewModel,
    resetTrigger: Int,
    onResetTrigger: () -> Unit
) {
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    val token = BuildConfig.FIREBASE_TOKEN
    val context = LocalContext.current

    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            user = result.user
            val userCurr = FirebaseAuth.getInstance().currentUser
            userCurr?.getIdToken(true)?.addOnSuccessListener { tokenResult ->
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
        onAuthError = {
            user = null
            Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
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
            SwipeButton(
                modifier = Modifier.padding(16.dp),
                onSwipeComplete = {
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
                },
                resetTrigger = resetTrigger
            )
        }
    }
}