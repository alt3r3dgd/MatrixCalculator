package me.altered.matrixcalculator

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.*
import me.altered.math.Matrix
import me.altered.matrixcalculator.ui.theme.*

@ExperimentalAnimationApi
@Composable
internal fun MatrixProperties(
    matrix: Matrix,
    onValueChange: (List<List<String>>) -> Unit
) {
    Column(Modifier.fillMaxWidth(), Arrangement.mediumSpacing) {
        val applyMatrix: (Matrix) -> Unit = { onValueChange(it.mapToList(Double::toString)) }
        Row(
            Modifier.padding(vertical = 8.dp),
            Arrangement.largeSpacing,
            Alignment.CenterVertically
        ) {
            Icon(Icons.Outlined.LooksOne, stringResource(R.string.determinant), tint = Green)
            Text("${stringResource(R.string.determinant)}: ${matrix.determinant}")
        }
        MatrixProperty(
            true,
            matrix.transposed,
            stringResource(R.string.transposed_matrix),
            Icons.Outlined.Loop,
            applyMatrix
        )
        MatrixProperty(
            matrix.isSquare && !matrix.isDegenerate,
            matrix.reversed,
            stringResource(R.string.reversed_matrix),
            Icons.Outlined.InvertColors,
            applyMatrix
        )
    }
}

@ExperimentalAnimationApi
@Composable
private fun MatrixProperty(
    visible: Boolean,
    matrix: Matrix?,
    name: String,
    icon: ImageVector,
    onApplyClick: (Matrix) -> Unit
) {
    if (!visible || matrix == null) return
    ExpandableText(name, icon) {
        Column(Modifier.defaultPadding, Arrangement.mediumSpacing) {
            val ss = rememberScrollState()
            matrix.run {
                Text(toString(), Modifier.horizontalScroll(ss), fontFamily = firaCode, lineHeight = 23.9.sp)
                IconTextButton(
                    { onApplyClick(this) },
                    stringResource(R.string.apply),
                    Icons.Outlined.Done
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun ExpandableText(
    text: String,
    icon: ImageVector,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    var expanded by true.remember()
    Column {
        Row(
            horizontalArrangement = Arrangement.largeSpacing,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, text, tint = Green)
            Text(text, Modifier.weight(1f, true))
            IconButton({ expanded = !expanded }) {
                Icon(
                    if (expanded) Icons.Outlined.ExpandLess
                    else Icons.Outlined.ExpandMore,
                    "Раскрыть",
                    Modifier.weight(1f),
                    Green
                )
            }
        }
        AnimatedVisibility(expanded, content = content)
    }
}