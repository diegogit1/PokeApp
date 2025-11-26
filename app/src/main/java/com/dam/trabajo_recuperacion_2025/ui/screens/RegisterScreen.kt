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
fun RegisterScreen(navController: NavController, authVm: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    val loading by authVm.loading.collectAsState()
    val message by authVm.message.collectAsState()
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement =
        Arrangement.Center) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label
        = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = email, onValueChange = { email = it },
            label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password =
            it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        Button({ authVm.register(email, password, name) }) {
            Text("Registrarse") }
        Spacer(Modifier.height(8.dp))
        Button({ navController.navigate("login") }) { Text("Ir a login") }
        Spacer(Modifier.height(8.dp))
        if (loading) Loading()
        if (message.isNotEmpty()) Text(message)
    }
}