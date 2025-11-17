package com.turis.gestiondetiempo.data.remote.service

import com.turis.gestiondetiempo.data.remote.client.GoogleCalendarClient
import com.turis.gestiondetiempo.model.CalendarEvent
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class CalendarApiService {

    private val client = GoogleCalendarClient.create()
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getCalendarEvents(accessToken: String): List<CalendarEvent> {
        val raw: String = client.get("calendars/primary/events") {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
        }.body()

        val root = json.parseToJsonElement(raw).jsonObject
        val items: JsonArray = root["items"]?.jsonArray ?: return emptyList()

        return items.mapNotNull { element ->
            val obj = element.jsonObject
            val id = obj["id"]?.jsonPrimitive?.contentOrNull ?: return@mapNotNull null
            val summary = obj["summary"]?.jsonPrimitive?.contentOrNull ?: "(Sin título)"
            val description = obj["description"]?.jsonPrimitive?.contentOrNull.orEmpty()
            val startObj = obj["start"]?.jsonObject ?: return@mapNotNull null

            val date = parseDateFromGoogle(startObj) ?: return@mapNotNull null

            CalendarEvent(
                id = id,
                title = summary,
                description = description,
                date = date,
                isDone = false
            )
        }
    }

    suspend fun addEvent(
        accessToken: String,
        title: String,
        description: String,
        date: LocalDate
    ): CalendarEvent {
        val endDate = date.plus(DatePeriod(days = 1))

        val bodyJson = JsonObject(
            mapOf(
                "summary" to JsonPrimitive(title),
                "description" to JsonPrimitive(description),
                "start" to JsonObject(mapOf("date" to JsonPrimitive(date.toString()))),
                "end" to JsonObject(mapOf("date" to JsonPrimitive(endDate.toString())))
            )
        )

        val raw: String = client.post("calendars/primary/events") {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
            setBody(bodyJson)
        }.body()

        val obj = json.parseToJsonElement(raw).jsonObject
        val id = obj["id"]?.jsonPrimitive?.contentOrNull ?: ""
        val summary = obj["summary"]?.jsonPrimitive?.contentOrNull ?: title
        val desc = obj["description"]?.jsonPrimitive?.contentOrNull ?: description
        val startObj = obj["start"]?.jsonObject
        val parsedDate = startObj?.let { parseDateFromGoogle(it) } ?: date

        return CalendarEvent(
            id = id,
            title = summary,
            description = desc,
            date = parsedDate,
            isDone = false
        )
    }

    suspend fun updateEvent(
        accessToken: String,
        event: CalendarEvent
    ): CalendarEvent {
        val endDate = event.date.plus(DatePeriod(days = 1))

        val bodyJson = JsonObject(
            mapOf(
                "summary" to JsonPrimitive(event.title),
                "description" to JsonPrimitive(event.description),
                "start" to JsonObject(mapOf("date" to JsonPrimitive(event.date.toString()))),
                "end" to JsonObject(mapOf("date" to JsonPrimitive(endDate.toString())))
            )
        )

        val raw: String = client.put("calendars/primary/events/${event.id}") {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
            setBody(bodyJson)
        }.body()

        val obj = json.parseToJsonElement(raw).jsonObject
        val summary = obj["summary"]?.jsonPrimitive?.contentOrNull ?: event.title
        val desc = obj["description"]?.jsonPrimitive?.contentOrNull ?: event.description
        val startObj = obj["start"]?.jsonObject
        val parsedDate = startObj?.let { parseDateFromGoogle(it) } ?: event.date

        return event.copy(
            title = summary,
            description = desc,
            date = parsedDate
        )
    }

    suspend fun deleteEvent(
        accessToken: String,
        eventId: String
    ) {
        client.delete("calendars/primary/events/$eventId") {
            header(HttpHeaders.Authorization, "Bearer $accessToken")
        }
    }

    private fun parseDateFromGoogle(startObj: JsonObject): LocalDate? {
        val dateOnly = startObj["date"]?.jsonPrimitive?.contentOrNull
        val dateTime = startObj["dateTime"]?.jsonPrimitive?.contentOrNull
        val raw = dateOnly ?: dateTime ?: return null

        val datePart = if (raw.length >= 10) raw.substring(0, 10) else raw
        return try {
            LocalDate.parse(datePart)
        } catch (_: Exception) {
            null
        }
    }
}