package com.turis.gestiondetiempo.data.local

import androidx.room.TypeConverter
import com.turis.gestiondetiempo.model.ChipInfo
import com.turis.gestiondetiempo.model.SubItem
import com.turis.gestiondetiempo.model.TaskTag
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate

class Converters {

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    fun toLocalDate(dateString: String): LocalDate {
        return LocalDate.parse(dateString)
    }

    @TypeConverter
    fun fromTaskTag(tag: TaskTag): String {
        return tag.name
    }

    @TypeConverter
    fun toTaskTag(tagName: String): TaskTag {
        return TaskTag.valueOf(tagName)
    }

    @TypeConverter
    fun fromChipInfoList(chips: List<ChipInfo>): String {
        return Json.encodeToString(chips)
    }

    @TypeConverter
    fun toChipInfoList(chipsString: String): List<ChipInfo> {
        return if (chipsString.isEmpty()) emptyList()
        else Json.decodeFromString(chipsString)
    }

    @TypeConverter
    fun fromSubItemList(subItems: List<SubItem>): String {
        return Json.encodeToString(subItems)
    }

    @TypeConverter
    fun toSubItemList(subItemsString: String): List<SubItem> {
        return if (subItemsString.isEmpty()) emptyList()
        else Json.decodeFromString(subItemsString)
    }
}