package `in`.iotkiit.raidersreckoningapp.view.components.core

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import `in`.iotkiit.raidersreckoningapp.ui.theme.modernWarfare

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    widthFraction: Float = 0.7f,
    fontSize: Int = 16
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        placeholder = {
            Text(
                text = placeholder,
                fontSize = fontSize.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = textStyle,
                fontFamily = modernWarfare
            )
        },
        textStyle = textStyle.copy(
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = fontSize.sp,
            fontFamily = modernWarfare
        ),
        modifier = modifier
            .border(2.dp, Color.Green, RoundedCornerShape(50))
            .fillMaxWidth(widthFraction),
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White.copy(0.6f),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White,
            disabledTextColor = Color.White.copy(0.6f),
            disabledContainerColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun CustomOutlinedTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    placeholder: String,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    widthFraction: Float = 0.7f,
    fontSize: Int = 16
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        placeholder = {
            Text(
                text = placeholder,
                fontSize = fontSize.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                style = textStyle,
                fontFamily = modernWarfare
            )
        },
        textStyle = textStyle.copy(
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = fontSize.sp,
            fontFamily = modernWarfare
        ),
        modifier = modifier
            .border(2.dp, Color.Green, RoundedCornerShape(50))
            .fillMaxWidth(widthFraction),
        shape = RoundedCornerShape(50),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White.copy(0.6f),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White,
            disabledTextColor = Color.White.copy(0.6f),
            disabledContainerColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}