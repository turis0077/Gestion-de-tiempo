package com.turis.gestiondetiempo.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.turis.gestiondetiempo.model.TaskTag

@Composable
fun DateChip(text: String) {
    AssistChip(
        onClick = { },
        label = { Text(text, fontSize = 12.sp) },
        leadingIcon = { Icon(Icons.Outlined.Schedule, null, modifier = Modifier.alpha(0.80f)) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        border = AssistChipDefaults.assistChipBorder(true),
        modifier = Modifier.padding(end = 8.dp)
    )
}

@Composable
fun InfoChip(text: String) {
    AssistChip(
        onClick = { },
        label = { Text(text, fontSize = 12.sp) },
        leadingIcon = { Icon(Icons.Outlined.Info, null, modifier = Modifier.alpha(0.80f)) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        border = AssistChipDefaults.assistChipBorder(true),
        modifier = Modifier.padding(end = 8.dp)
    )
}

@Composable
fun TimeChip(text: String) {
    AssistChip(
        onClick = { },
        label = { Text(text, fontSize = 12.sp) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        border = AssistChipDefaults.assistChipBorder(true),
        modifier = Modifier.padding(end = 8.dp)
    )
}

@Composable
fun TagPill(tag: TaskTag) {
    AssistChip(
        onClick = { },
        label = { Text(tag.label, fontSize = 12.sp) },
        leadingIcon = { Icon(Icons.Outlined.BookmarkBorder, null) },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = tag.tint,
            labelColor = MaterialTheme.colorScheme.onSurface
        ),
        border = AssistChipDefaults.assistChipBorder(true),
        modifier = Modifier.padding(end = 8.dp)
    )
}
