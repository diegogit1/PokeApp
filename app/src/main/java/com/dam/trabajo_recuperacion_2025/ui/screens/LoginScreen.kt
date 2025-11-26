package com.dam.trabajo_recuperacion_2025.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import com.dam.trabajo_recuperacion_2025.ui.components.Loading
import androidx.navigation.NavController
import com.dam.trabajo_recuperacion_2025.viewmodel.AuthViewModel
@Composable
fun LoginScreen(navController: NavController, authVm: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loading by authVm.loading.collectAsState()
    val message by authVm.message.collectAsState()
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement =
        Arrangement.Center) {
        OutlinedTextField(value = email, onValueChange = { email = it },
            label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = password, onValueChange = { password =
            it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        Button({ authVm.login(email, password) }) { Text("Entrar") }
        Spacer(Modifier.height(8.dp))
        Button({ navController.navigate("register") }) { Text("Ir a registro") }
                Spacer(Modifier.height(8.dp))
            if (loading) Loading()
            if (message.isNotEmpty()) Text(message)
        }
    }
