package com.turis.gestiondetiempo.features

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.turis.gestiondetiempo.ui.tags.LabelTag
import com.turis.gestiondetiempo.ui.tags.resolve

@Composable
fun TagsDropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    tags: List<LabelTag>,
    onSelect: (LabelTag) -> Unit,
    onEdit: (LabelTag) -> Unit,
    onCreateNew: () -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        modifier = modifier.width(260.dp),
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Text(
            "Etiquetas",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(12.dp)
        )
        Divider()

        tags.forEach { tag ->
            val colors = tag.color.resolve()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(colors.container)
                    .clickable {
                        onSelect(tag)
                        onDismiss()
                    }
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    tag.name,
                    modifier = Modifier.weight(1f),
                    color = colors.onContainer,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = "Editar",
                    tint = colors.onContainer,
                )
            }
            Spacer(Modifier.height(4.dp))
        }

        Text(
            "Nueva etiqueta +",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onDismiss()
                    onCreateNew()
                }
                .padding(12.dp)
        )
    }
}