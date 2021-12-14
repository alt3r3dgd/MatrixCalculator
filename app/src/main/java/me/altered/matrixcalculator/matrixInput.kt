package me.altered.matrixcalculator

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
internal fun MatrixInput(
    rows: Int,
    columns: Int,
    value: List<List<String>>,
    onValueChange: (List<List<String>>) -> Unit
) = LazyRow(
    Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
) {
    items(columns) { column ->
        Column(verticalArrangement = Arrangement.largeSpacing) {
            repeat(rows) { row ->
                val number = value.getOrNull(row)?.getOrNull(column) ?: ""
                OutlinedTextField(
                    number,
                    { onValueChange(value.update(row, value[row].update(column, it))) },
                    Modifier.width(120.dp),
                    label = { Text("${row + 1}; ${column + 1}") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true
                )
            }
        }
    }
}