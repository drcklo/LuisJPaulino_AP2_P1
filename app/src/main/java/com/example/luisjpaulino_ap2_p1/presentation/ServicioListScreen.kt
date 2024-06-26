package com.example.luisjpaulino_ap2_p1.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.luisjpaulino_ap2_p1.data.remote.dto.ServicioDto

@Composable
fun ServicioListScreen(
    viewModel: ServicioViewModel = hiltViewModel(),
    verSevicio: (ServicioDto) -> Unit,
    onAddServicio: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ServicioListBody(
        uiState = uiState,
        servicios = uiState.servicios,
        onAddServicio = onAddServicio,
        onVerServicio = verSevicio
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicioListBody(
    uiState: ServicioViewModel.ServicioUIState,
    viewModel: ServicioViewModel = hiltViewModel(),
    servicios: List<ServicioDto>,
    onAddServicio: () -> Unit,
    onVerServicio: (ServicioDto) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Lista de Servicios ")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddServicio) {
                Icon(Icons.Filled.Add, "Agregar nuevo servicio")
            }

        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "ID", modifier = Modifier.weight(0.5f))
                Text(text = "Descripcion", modifier = Modifier.weight(1f))
                Text(text = "Precio", modifier = Modifier.weight(1f))
            }

            HorizontalDivider()

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(servicios) { servicio ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onVerServicio(servicio) }
                    ) {
                        Text(
                            text = servicio.servicioId.toString(),
                            modifier = Modifier.weight(0.5f)
                        )
                        Text(text = servicio.descripcion ?: "", modifier = Modifier.weight(1f))
                        Text(
                            text = servicio.precio.toString() + "$",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

