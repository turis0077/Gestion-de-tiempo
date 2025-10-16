package com.turis.gestiondetiempo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.turis.gestiondetiempo.ui.profile.ProfileScreen
import com.turis.gestiondetiempo.ui.tasks.TaskListScreen
import com.turis.gestiondetiempo.ui.tasks.TaskTemplateScreen
import com.turis.gestiondetiempo.model.sampleTaskDetail
import com.turis.gestiondetiempo.model.sampleTaskListFull

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TaskTemplateScreen(task = sampleTaskDetail())
            }
        }
    }
}
