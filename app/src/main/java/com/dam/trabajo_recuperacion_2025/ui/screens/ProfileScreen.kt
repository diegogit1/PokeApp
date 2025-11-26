package com.dam.trabajo_recuperacion_2025.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dam.trabajo_recuperacion_2025.ui.components.Loading
import com.dam.trabajo_recuperacion_2025.viewmodel.AuthViewModel
@Composable
fun ProfileScreen(navController: NavController, authVm: AuthViewModel) {
    val user by authVm.currentUser.collectAsState()
    var name by remember { mutableStateOf(user?.get("name") as? String ?:
    "") }
    var email by remember { mutableStateOf(user?.get("email") as? String ?:
    "") }
    val loading by authVm.loading.collectAsState()
    val message by authVm.message.collectAsState()
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement =
        Arrangement.Top) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label
        = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = email, onValueChange = { email = it },
            label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), enabled =
                false)
        Spacer(Modifier.height(16.dp))
        Button({ authVm.updateProfile(name) }) { Text("Guardar") }
        Spacer(Modifier.height(8.dp))
        Button({ authVm.logout(); navController.navigate("inicio") }) {
            Text("Cerrar sesión") }
        Spacer(Modifier.height(8.dp))
        if (loading) Loading()
        if (message.isNotEmpty()) Text(message)
    }
}