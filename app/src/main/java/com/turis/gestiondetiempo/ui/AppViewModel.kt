package com.turis.gestiondetiempo.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turis.gestiondetiempo.model.Task
import com.turis.gestiondetiempo.model.TaskTag
import com.turis.gestiondetiempo.model.sampleTaskListFull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * ViewModel principal de la aplicación que maneja:
 * - Perfil de usuario (nombre, foto)
 * - Lista de tareas y su sincronización
 */
class AppViewModel : ViewModel() {

    // Estado del perfil de usuario
    private val _username = MutableStateFlow("User1259")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _profilePhotoUri = MutableStateFlow<Uri?>(null)
    val profilePhotoUri: StateFlow<Uri?> = _profilePhotoUri.asStateFlow()

    // Estado de las tareas (lista plana de todas las tareas)
    private val _tasks = MutableStateFlow<List<Task>>(getAllTasksFromSections())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    // Función para obtener todas las tareas de las secciones de muestra
    private fun getAllTasksFromSections(): List<Task> {
        val sections = sampleTaskListFull()
        return sections.flatMap { it.items }
    }

    // Generar el siguiente ID de tarea
    private fun generateNextTaskId(): String {
        val currentIds = _tasks.value.map { it.id.toIntOrNull() ?: 0 }
        val maxId = currentIds.maxOrNull() ?: 0
        return String.format("%04d", maxId + 1)
    }

    // Actualizar nombre de usuario
    fun updateUsername(newUsername: String) {
        viewModelScope.launch {
            _username.emit(newUsername)
        }
    }

    // Actualizar foto de perfil
    fun updateProfilePhoto(uri: Uri?) {
        viewModelScope.launch {
            _profilePhotoUri.emit(uri)
        }
    }

    // Obtener tarea por ID
    fun getTaskById(taskId: String): Task? {
        return _tasks.value.find { it.id == taskId }
    }

    // Actualizar una tarea completa
    fun updateTask(updatedTask: Task) {
        viewModelScope.launch {
            val newList = _tasks.value.map { task ->
                if (task.id == updatedTask.id) updatedTask else task
            }
            _tasks.emit(newList)
        }
    }

    // Marcar tarea como completada y eliminarla de la lista
    fun toggleTaskCompletion(taskId: String, completed: Boolean) {
        viewModelScope.launch {
            if (completed) {
                // Si se marca como completada, eliminarla de la lista
                val newList = _tasks.value.filter { it.id != taskId }
                _tasks.emit(newList)
            } else {
                // Si se desmarca, actualizar el estado
                val newList = _tasks.value.map { task ->
                    if (task.id == taskId) task.copy(completed = completed) else task
                }
                _tasks.emit(newList)
            }
        }
    }

    // Crear una nueva tarea
    fun createTask(title: String, date: LocalDate, tag: TaskTag): Task {
        val newTask = Task(
            id = generateNextTaskId(),
            title = title,
            date = date,
            tag = tag,
            description = ""
        )

        viewModelScope.launch {
            val newList = _tasks.value + newTask
            _tasks.emit(newList)
        }

        return newTask
    }
}