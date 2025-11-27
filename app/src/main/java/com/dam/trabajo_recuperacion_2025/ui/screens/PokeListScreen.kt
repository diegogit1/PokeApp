package com.dam.trabajo_recuperacion_2025.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.dam.trabajo_recuperacion_2025.viewmodel.PokeViewModel
import kotlinx.coroutines.launch
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PokeListScreen(
    navController: NavController,
    pokeVm: PokeViewModel
) {
    var count by remember { mutableStateOf(30f) }
    val list by pokeVm.pokemonList.collectAsState()
    val loading by pokeVm.loading.collectAsState()
    val message by pokeVm.message.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        pokeVm.loadPokemon(count.toInt())
    }

    LaunchedEffect(message) {
        if (message.isNotBlank()) {
            scope.launch { /* opcional: manejar mensaje */ }
        }
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = loading,
        onRefresh = { pokeVm.loadPokemon(count.toInt()) }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Mostrar: ${count.toInt()}")
            Button(onClick = { pokeVm.loadPokemon(count.toInt()) }) {
                Text("Cargar")
            }
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Slider(
            value = count,
            onValueChange = { count = it },
            valueRange = 1f..151f,
            steps = 150,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {

            if (loading && list.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    CircularProgressIndicator()
                }
            }

            if (message.isNotBlank() && list.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = message)
                    Spacer(modifier = Modifier.size(8.dp))
                    Button(onClick = { pokeVm.loadPokemon(count.toInt()) }) { Text("Reintentar") }
                }
            }

            if (list.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(list) { p ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row(Modifier.padding(8.dp)) {
                                AsyncImage(
                                    model = p.image,
                                    contentDescription = p.name,
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(p.name)
                            }
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = loading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}




