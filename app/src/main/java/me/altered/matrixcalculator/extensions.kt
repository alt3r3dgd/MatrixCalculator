package me.altered.matrixcalculator

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.altered.math.Matrix

internal val Modifier.defaultPadding get() = padding(12.dp)
internal val Arrangement.largeSpacing get() = spacedBy(16.dp)
internal val Arrangement.mediumSpacing get() = spacedBy(12.dp)
internal val Arrangement.smallSpacing get() = spacedBy(8.dp)

@Composable
internal fun <T> T.remember() = remember { mutableStateOf(this) }

internal fun newStringList(rows: Int, columns: Int) = List(rows) { List(columns) { "" } }

internal fun List<List<String>>.toMatrix() = Matrix(map { it.map { num -> num.toDoubleOrNull() ?: 0.0 } })

internal fun <T> List<T>.update(index: Int, item: T) = toMutableList().apply { this[index] = item }.toList()

internal fun List<List<String>>.resize(rows: Int, columns: Int) = when {
    rows == size -> map {
        (0 until columns).map { column ->
            it.getOrElse(column) { "" }
        }
    }
    columns == this[0].size -> (0 until rows).map {
        if (it >= size)
            List(columns) { "" }
        else
            this[it]
    }
    else -> (0 until rows).map { row ->
        if (row >= size)
            List(columns) { "" }
        else
            (0 until columns).map { column ->
                this[row].getOrElse(column) { "" }
            }
    }
}

