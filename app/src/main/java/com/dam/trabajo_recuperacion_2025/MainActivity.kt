package com.dam.trabajo_recuperacion_2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dam.trabajo_recuperacion_2025.navigation.NavGraph
import com.dam.trabajo_recuperacion_2025.ui.theme.Trabajo_recuperacion_2025Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Trabajo_recuperacion_2025Theme {
                Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier) {
                    NavGraph()
                }
            }
        }
    }
}
