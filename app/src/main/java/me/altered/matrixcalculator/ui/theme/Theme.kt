package me.altered.matrixcalculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import me.altered.matrixcalculator.R

private val DarkColorPalette = darkColors(
    primary = Green,
    primaryVariant = GreenVariant,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Green,
    primaryVariant = GreenVariant,
    secondary = Teal200
)

val colors @Composable get() = if (isSystemInDarkTheme()) DarkColorPalette else LightColorPalette

@Composable
fun MatrixCalculatorTheme(content: @Composable () -> Unit) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(colors.primaryVariant)
    systemUiController.setNavigationBarColor(Color.Transparent)
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

val firaCode = FontFamily(
    Font(R.font.firacode_retina, FontWeight.Normal),
    Font(R.font.firacode_medium, FontWeight.Medium),
    Font(R.font.firacode_bold, FontWeight.Bold),
    Font(R.font.firacode_semibold, FontWeight.SemiBold),
    Font(R.font.firacode_light, FontWeight.Light)
)