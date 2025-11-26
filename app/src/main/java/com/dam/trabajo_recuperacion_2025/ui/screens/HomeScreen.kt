package com.dam.trabajo_recuperacion_2025.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dam.trabajo_recuperacion_2025.viewmodel.AuthViewModel
@Composable
fun HomeScreen(navController: NavController, authVm: AuthViewModel) {
    Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment =
        Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Bienvenido a PokeApp")
        Spacer(Modifier.height(16.dp))
        Button({ navController.navigate("login") }) { Text("Iniciar sesión") }
                Spacer(Modifier.height(8.dp))
                Button({ navController.navigate("register") }) {
            Text("Registrarse") }
                Spacer(Modifier.height(8.dp))
                Button({ navController.navigate("pokelist") }) { Text("Explorar Pokémon") }
                Spacer(Modifier.height(8.dp))
                Button({ navController.navigate("profile") }) { Text("Mi perfil") }
        }
        }
