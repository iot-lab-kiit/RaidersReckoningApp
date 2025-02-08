package `in`.iotkiit.raidersreckoningapp.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import `in`.iotkiit.raidersreckoningapp.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateTeamScreen(navController: NavHostController) {

    val teamName = remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = painterResource(R.drawable.title),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth(0.75f)
                )

                Column {
                    OutlinedTextField(
                        value = teamName.value,
                        onValueChange = {
                            teamName.value = it
                        },
                        placeholder = {
                            Text(
                                "TEAM NAME",
                                fontSize = 24.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        textStyle = TextStyle(
                            color = Color.White,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .border(2.dp, Color.Green, RoundedCornerShape(50))
                            .fillMaxWidth(0.7f),
                        shape = RoundedCornerShape(50),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White.copy(0.6f),
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.White
                        )
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = {
                            //Submit Team
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .border(1.dp, Color.Green, RoundedCornerShape(50)),
                        colors = ButtonDefaults.primaryButtonColors(
                            backgroundColor = Color.Green
                        )
                    ) {
                        Text(
                            text = "SUBMIT",
                            fontSize = 24.sp,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

            }
        }
    }

}