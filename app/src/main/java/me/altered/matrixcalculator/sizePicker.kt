package me.altered.matrixcalculator

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import com.chargemap.compose.numberpicker.NumberPicker

@Composable
internal fun SizePicker(
    rows: Int,
    columns: Int,
    onValueChange: (r: Int, c: Int) -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        Alignment.CenterVertically
    ) {
        NumberPicker(
            value = rows,
            onValueChange = { onValueChange(it, columns) },
            range = 1..10
        )
        Text("x")
        NumberPicker(
            value = columns,
            onValueChange = { onValueChange(rows, it) },
            range = 1..10
        )
    }
}