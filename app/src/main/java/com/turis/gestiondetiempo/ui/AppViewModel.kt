package com.turis.gestiondetiempo.ui

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.turis.gestiondetiempo.data.local.AppDatabase
import com.turis.gestiondetiempo.data.local.toEntity
import com.turis.gestiondetiempo.data.local.toTask
import com.turis.gestiondetiempo.model.Task
import com.turis.gestiondetiempo.model.TaskTag
import com.turis.gestiondetiempo.model.sampleTaskListFull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * ViewModel principal de la aplicación que maneja:
 * - Perfil de usuario (nombre, foto)
 * - Lista de tareas y su sincronización con Room
 */
class AppViewModel(application: Application) : AndroidViewModel(application) {

    // Database y DAO
    private val database = AppDatabase.getDatabase(application)
    private val taskDao = database.taskDao()

    // Estado del perfil de usuario
    private val _username = MutableStateFlow("User1259")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _profilePhotoUri = MutableStateFlow<Uri?>(null)
    val profilePhotoUri: StateFlow<Uri?> = _profilePhotoUri.asStateFlow()

    // Estado de las tareas (desde Room)
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        // Cargar tareas desde Room
        viewModelScope.launch {
            taskDao.getAllTasks().map { entities ->
                entities.map { it.toTask() }
            }.collect { taskList ->
                _tasks.value = taskList
            }
        }

        // Inicializar con datos de muestra si la base de datos está vacía
        viewModelScope.launch {
            val currentTasks = taskDao.getAllTasks()
            currentTasks.collect { entities ->
                if (entities.isEmpty()) {
                    // Insertar tareas de muestra
                    val sampleTasks = getAllTasksFromSections()
                    sampleTasks.forEach { task ->
                        taskDao.insertTask(task.toEntity())
                    }
                }
            }
        }
    }

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

    // Actualizar contraseña
    fun updatePassword(newPassword: String) {
        viewModelScope.launch {
            _password.emit(newPassword)
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
            taskDao.updateTask(updatedTask.toEntity())
        }
    }

    // Marcar tarea como completada y eliminarla de la lista
    fun toggleTaskCompletion(taskId: String, completed: Boolean) {
        viewModelScope.launch {
            if (completed) {
                // Si se marca como completada, eliminarla de la base de datos
                taskDao.deleteTaskById(taskId)
            } else {
                // Si se desmarca, actualizar el estado
                val task = taskDao.getTaskById(taskId)
                task?.let {
                    taskDao.updateTask(it.copy(completed = completed))
                }
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
            taskDao.insertTask(newTask.toEntity())
        }

        return newTask
    }

    // Eliminar una tarea
    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            taskDao.deleteTaskById(taskId)
        }
    }
}