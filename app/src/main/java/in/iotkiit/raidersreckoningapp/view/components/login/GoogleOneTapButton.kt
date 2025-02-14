package `in`.iotkiit.raidersreckoningapp.view.components.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import `in`.iotkiit.raidersreckoningapp.R
import `in`.iotkiit.raidersreckoningapp.ui.theme.GreenCOD

@Composable
fun GoogleOneTapButton(
    iconOnly: Boolean = false,
    theme: GoogleButtonTheme = if (isSystemInDarkTheme()) GoogleButtonTheme.Dark
    else GoogleButtonTheme.Light,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = when (theme) {
            GoogleButtonTheme.Light -> Color.White
            GoogleButtonTheme.Dark -> Color(0xFF131314)
            GoogleButtonTheme.Neutral -> Color(0xFFF2F2F2)
        },
        contentColor = when (theme) {
            GoogleButtonTheme.Dark -> Color(0xFFE3E3E3)
            else -> Color(0xFF1F1F1F)
        },
    ),
    border: BorderStroke? = when (theme) {
        GoogleButtonTheme.Light -> BorderStroke(
            width = 1.dp,
            color = Color(0xFF747775),
        )

        GoogleButtonTheme.Dark -> BorderStroke(
            width = 1.dp,
            color = Color(0xFF747775),
        )

        GoogleButtonTheme.Neutral -> null
    },
    shape: Shape = ButtonDefaults.shape,
    onClick: (() -> Unit)? = null,
) {
    Button(
        modifier = Modifier.width(if (iconOnly) 40.dp else Dp.Unspecified),
        onClick = {
            onClick?.invoke()
        },
        shape = shape,
        colors = colors,
        contentPadding = PaddingValues(horizontal = if (iconOnly) 9.5.dp else 12.dp),
        border = border,
    ) {
        Box(
            modifier = Modifier.padding(end = if (iconOnly) 0.dp else 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = "Google Logo"
            )
        }
        if (!iconOnly) {
            Text(
                text = "Sign in with Google",
                maxLines = 1,
                fontFamily = MetroPolisFontFamily,
            )
        }
    }
}

enum class GoogleButtonTheme { Light, Dark, Neutral }

private val MetroPolisFontFamily = FontFamily(
    Font(
        resId = R.font.metropolis_medium, weight = FontWeight.Medium, style = FontStyle.Normal
    )
)

@Preview
@Composable
private fun LightIconButtonPreview() {
    GoogleOneTapButton(
        theme = GoogleButtonTheme.Light,
        iconOnly = true,
    )
}

@Preview
@Composable
private fun DarkIconButtonPreview() {
    GoogleOneTapButton(
        theme = GoogleButtonTheme.Dark,
        iconOnly = true,
    )
}

@Preview
@Composable
private fun NeutralIconButtonPreview() {
    GoogleOneTapButton(
        theme = GoogleButtonTheme.Neutral,
        iconOnly = true,
    )
}

@Preview
@Composable
private fun LightFullButtonPreview() {
    GoogleOneTapButton(
        theme = GoogleButtonTheme.Light,
    )
}

@Preview
@Composable
private fun DarkFullButtonPreview() {
    GoogleOneTapButton(
        theme = GoogleButtonTheme.Dark,
    )
}

@Preview
@Composable
private fun NeutralFullButtonPreview() {
    GoogleOneTapButton(
        theme = GoogleButtonTheme.Neutral,
    )
}
