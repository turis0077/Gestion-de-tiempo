package com.turis.gestiondetiempo.ui.tags

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.collections.listOf

enum class TagSwatch {
    PrimaryContainer,
    SecondaryContainer,
    TertiaryContainer,
    Forth,
    Fifth
}

data class LabelTag(
    val id: String,
    val name: String,
    val color: TagSwatch
)

class TagsViewModel : ViewModel() {
    var tags by mutableStateOf(
        listOf(
            LabelTag("work","Trabajo", TagSwatch.PrimaryContainer),
            LabelTag("study", "Estudios", TagSwatch.SecondaryContainer),
            LabelTag("hobbies","Hobies", TagSwatch.TertiaryContainer),
            LabelTag("personal", "Personal", TagSwatch.Forth)
        )
    )
        private set

    var selectedTag by mutableStateOf<LabelTag?>(null)
        private set

    fun select(tag: LabelTag) { selectedTag = tag }

    fun upsert(tag: LabelTag) {
        val i = tags.indexOfFirst { it.id == tag.id }
        tags = if (i >= 0) tags.toMutableList().also { it[i] = tag }
               else tags + tag
    }

    fun deleteTag(id: String) {
        tags = tags.filterNot { it.id == id }
        if (selectedTag?.id == id) selectedTag = null
    }

    fun createNewTag(name: String, color: TagSwatch = TagSwatch.SecondaryContainer) {
        val newTag = LabelTag(
            id = name.lowercase(),
            name = name,
            color = color
        )
        tags = tags + newTag
    }

}