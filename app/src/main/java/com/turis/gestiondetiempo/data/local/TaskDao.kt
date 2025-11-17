package com.turis.gestiondetiempo.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks ORDER BY date ASC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE user = :username OR user = '' ORDER BY date ASC")
    fun getTasksByUser(username: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: String): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTaskById(taskId: String)

    @Query("SELECT * FROM tasks WHERE completed = 0 AND (user = :username OR user = '') ORDER BY date ASC")
    fun getActiveTasksByUser(username: String): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE completed = 1 AND (user = :username OR user = '') ORDER BY date DESC")
    fun getCompletedTasksByUser(username: String): Flow<List<TaskEntity>>
}