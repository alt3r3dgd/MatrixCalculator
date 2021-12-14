package me.altered.matrixcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import me.altered.matrixcalculator.ui.theme.*

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MatrixCalculatorTheme { Content() } }
    }
}

@ExperimentalAnimationApi
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Preview() = MatrixCalculatorTheme { Content(0, 2, 2) }

@ExperimentalAnimationApi
@Composable
private fun Content(startTab: Int = 0, startRows: Int = 2, startColumns: Int = 2) {
    val scrollState = rememberScrollState()
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(stringResource(R.string.app_name)) },
            backgroundColor = colors.primary,
            contentColor = colors.background
        )
    }) {
        Column(
            Modifier
                .defaultPadding
                .verticalScroll(scrollState)
                .fillMaxWidth(),
            Arrangement.largeSpacing,
            Alignment.CenterHorizontally
        ) {
            var visible by false.remember()
            var isNumber by false.remember()
            var content1 by newStringList(startRows, startColumns).remember()
            var content2 by newStringList(startRows, startColumns).remember()
            var number by "".remember()
            MatrixCard(content1, startTab) { content1 = it }
            AnimatedVisibility(!visible) {
                Row(horizontalArrangement = Arrangement.largeSpacing) {
                    IconTextButton(
                        { visible = !visible; isNumber = false },
                        stringResource(R.string.matrix),
                        Icons.Outlined.Grid3x3
                    )
                    IconTextButton(
                        { visible = !visible; isNumber = true },
                        stringResource(R.string.number),
                        Icons.Outlined.Pin
                    )
                }
            }
            AnimatedVisibility(visible) {
                Column(
                    verticalArrangement = Arrangement.largeSpacing,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isNumber) {
                        NumberActions(content1, number) {
                            content1 = it
                            number = ""
                            visible = false
                        }
                        OutlinedTextField(
                            number,
                            { number = it },
                            label = { Text(stringResource(R.string.number)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true
                        )
                    } else {
                        SecondMatrixActions(content1, content2) {
                            content1 = it
                            content2 = newStringList(startRows, startColumns)
                            visible = false
                        }
                        MatrixCard(content2, startTab) { content2 = it }
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun MatrixCard(value: List<List<String>>, startTab: Int = 0, onValueChange: (List<List<String>>) -> Unit) {
    var tab by startTab.remember()
    val tabs = listOf(
        stringResource(R.string.numbers) to Icons.Outlined.Pin,
        stringResource(R.string.size) to Icons.Outlined.AspectRatio,
        stringResource(R.string.properties) to Icons.Outlined.Assessment
    )
    val rows = value.size
    val columns = value[0].size
    val matrix = value.toMatrix()

    Card {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TabRow(tab, backgroundColor = Color.Transparent, contentColor = Green) {
                tabs.forEachIndexed { index, (name, icon) ->
                    Tab(
                        tab == index,
                        { tab = index },
                        text = { Text(name) },
                        icon = { Icon(icon, name) }
                    )
                }
            }
            Crossfade(tab, Modifier.defaultPadding) {
                when (it) {
                    0 -> MatrixInput(rows, columns, value, onValueChange)
                    1 -> SizePicker(rows, columns) { r, c -> onValueChange(value.resize(r, c)) }
                    2 -> MatrixProperties(matrix) { newMatrix ->
                        onValueChange(newMatrix)
                        tab = 0
                    }
                }
            }
        }
    }
}

@Composable
internal fun IconTextButton(onClick: () -> Unit, text: String, icon: ImageVector) =
    OutlinedButton(onClick) {
        Row(
            horizontalArrangement = Arrangement.smallSpacing,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, text)
            Text(text)
        }
    }


