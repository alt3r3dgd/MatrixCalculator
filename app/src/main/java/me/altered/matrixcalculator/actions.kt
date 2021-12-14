package me.altered.matrixcalculator

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.altered.math.Matrix

@Composable
internal fun NumberActions(
    content1: List<List<String>>,
    number: String,
    onValueChange: (List<List<String>>) -> Unit
) {
    Row(horizontalArrangement = Arrangement.largeSpacing) {
        val matrix = Matrix(content1.map { it.map { num -> num.toDoubleOrNull() ?: 0.0 } })
        val num = number.toDoubleOrNull() ?: 0.0
        IconButton({ onValueChange(matrix.mapToList { (it + num).toString() }) }) {
            Icon(Icons.Outlined.Add, stringResource(R.string.add_number_to_matrix))
        }
        IconButton({ onValueChange(matrix.mapToList { (it - num).toString() }) }) {
            Icon(Icons.Outlined.Remove, stringResource(R.string.subtract_number_from_matrix))
        }
        IconButton({ onValueChange(matrix.mapToList { (it * num).toString() }) }) {
            Icon(Icons.Outlined.Clear, stringResource(R.string.multiply_matrix_by_number))
        }
    }
}

@Composable
internal fun SecondMatrixActions(
    content1: List<List<String>>,
    content2: List<List<String>>,
    onValueChange: (List<List<String>>) -> Unit
) {
    Row(horizontalArrangement = Arrangement.largeSpacing) {
        val matrix1 = Matrix(content1.map { it.map { num -> num.toDoubleOrNull() ?: 0.0 } })
        val matrix2 = Matrix(content2.map { it.map { num -> num.toDoubleOrNull() ?: 0.0 } })
        IconButton({ onValueChange((matrix1 + matrix2).mapToList(Double::toString)) }) {
            Icon(Icons.Outlined.Add, stringResource(R.string.add_matrices))
        }
        IconButton({ onValueChange((matrix1 - matrix2).mapToList(Double::toString)) }) {
            Icon(Icons.Outlined.Remove, stringResource(R.string.subtract_matrices))
        }
        if (matrix1.width == matrix2.height)
            IconButton({ onValueChange((matrix1 * matrix2)!!.mapToList(Double::toString)) }) {
                Icon(Icons.Outlined.Clear, stringResource(R.string.multiply_matrices))
            }
    }
}