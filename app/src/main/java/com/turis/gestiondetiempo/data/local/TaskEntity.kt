package com.turis.gestiondetiempo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.turis.gestiondetiempo.model.ChipInfo
import com.turis.gestiondetiempo.model.SubItem
import com.turis.gestiondetiempo.model.Task
import com.turis.gestiondetiempo.model.TaskTag
import java.time.LocalDate

@Entity(tableName = "tasks")
@TypeConverters(Converters::class)
data class TaskEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val date: LocalDate,
    val tag: TaskTag,
    val chips: List<ChipInfo>,
    val subItems: List<SubItem>,
    val completed: Boolean,
    val description: String,
    val timeMinutes: Int,
    val timeSeconds: Int,
    val user: String  // Usuario propietario
)

fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        date = date,
        tag = tag,
        chips = chips,
        subItems = subItems,
        completed = completed,
        description = description,
        timeMinutes = timeMinutes,
        timeSeconds = timeSeconds,
        user = user
    )
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        title = title,
        date = date,
        tag = tag,
        chips = chips,
        subItems = subItems,
        completed = completed,
        description = description,
        timeMinutes = timeMinutes,
        timeSeconds = timeSeconds,
        user = user
    )
}