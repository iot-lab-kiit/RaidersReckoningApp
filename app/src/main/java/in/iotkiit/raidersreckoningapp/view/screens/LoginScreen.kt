package `in`.iotkiit.raidersreckoningapp.view.screens

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import `in`.iotkiit.raidersreckoningapp.BuildConfig
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.state.UiState
import `in`.iotkiit.raidersreckoningapp.view.components.login.rememberFirebaseAuthLauncher
import `in`.iotkiit.raidersreckoningapp.vm.DashBoardViewModel
import kotlin.math.roundToInt

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
    var resetTrigger by remember { mutableIntStateOf(0) }

    val launcher = rememberFirebaseAuthLauncher(onAuthComplete = { result ->
        user = result.user
        val userCurr = FirebaseAuth.getInstance().currentUser
        userCurr?.getIdToken(true)?.addOnSuccessListener { tokenResult ->
            val idToken = tokenResult.token
            if (idToken != null) {
                Log.d("Google Auth", "Token: $idToken")
                dashBoardViewModel.verifyToken(idToken)
            } else {
                Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
                resetTrigger++
            }
        }?.addOnFailureListener {
            Toast.makeText(context, "Failed to fetch ID token!", Toast.LENGTH_LONG).show()
            resetTrigger++
        }
    }, onAuthError = {
        user = null
        Toast.makeText(context, "Login Failed!", Toast.LENGTH_LONG).show()
        resetTrigger++
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
            resetTrigger++
        }

        else -> Unit
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
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
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                    .size(buttonHeight)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
               Image(
                   painter = painterResource(R.drawable.google_logo),
                   contentDescription = null
               )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sign In",
                    fontSize = 24.sp,
                    color = Color.White
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