package com.dam.trabajo_recuperacion_2025.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.dam.trabajo_recuperacion_2025.ui.screens.*
import com.dam.trabajo_recuperacion_2025.viewmodel.AuthViewModel
import com.dam.trabajo_recuperacion_2025.viewmodel.PokeViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val authVm: AuthViewModel = viewModel()
    val pokeVm: PokeViewModel = viewModel()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "inicio"

    BackHandler(enabled = navController.previousBackStackEntry != null) {
        navController.navigateUp()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                navController = navController,
                title = when (currentRoute) {
                    "inicio" -> "Inicio"
                    "login" -> "Login"
                    "register" -> "Registro"
                    "profile" -> "Mi perfil"
                    "pokelist" -> "Pokémon"
                    else -> ""
                }
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "inicio",
            modifier = Modifier.padding(padding)
        ) {
            composable("inicio") { HomeScreen(navController, authVm) }
            composable("login") { LoginScreen(navController, authVm) }
            composable("register") { RegisterScreen(navController, authVm) }
            composable("profile") { ProfileScreen(navController, authVm) }
            composable("pokelist") { PokeListScreen(navController, pokeVm) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(navController: NavHostController, title: String) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (navController.previousBackStackEntry != null) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                }
            }
        }
    )
}
