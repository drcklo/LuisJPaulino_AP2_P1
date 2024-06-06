package com.example.luisjpaulino_ap2_p1.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ServicioScreen(
    viewModel: ServicioViewModel, volverALista: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ServicioBody(uiState = uiState,
        onDescripcionChange = { viewModel.onDescripcionChange(it) },
        onPrecioChange = { viewModel.onPrecioChange(it) },
        onSaveServicio = { viewModel.saveServicio() },
        onDeleteServicio = { viewModel.borrarServicio() },
        limpiarServicio = { viewModel.limpiarServicio() },
        volverALista = volverALista,
        validation = { viewModel.validation() })

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServicioBody(

    uiState: ServicioUIState,
    onDescripcionChange: (String) -> Unit,
    onPrecioChange: (String) -> Unit,
    onSaveServicio: () -> Unit,
    onDeleteServicio: () -> Unit,
    limpiarServicio: () -> Unit,
    validation: () -> Boolean,
    volverALista: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = "Nuevo Servicio") }, navigationIcon = {
                IconButton(onClick = { volverALista() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack, "Volver a la lista"
                    )
                }
            })
        },

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .padding(it)
        ) {

            ElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    OutlinedTextField(
                        label = { Text("Descripción") },
                        value = uiState.descripcion ?: "",
                        onValueChange = onDescripcionChange,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (uiState.errorDescripcion != null) {
                        Text(
                            text = uiState.errorDescripcion!!,
                            color = androidx.compose.ui.graphics.Color.Red
                        )
                    }

                    OutlinedTextField(
                        label = { Text("Precio") },
                        value = uiState.precio.toString(),
                        onValueChange = onPrecioChange,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (uiState.errorPrecio != null) {
                        Text(
                            text = uiState.errorPrecio!!,
                            color = androidx.compose.ui.graphics.Color.Red
                        )
                    }

                    Spacer(modifier = Modifier.padding(2.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {

                        Spacer(modifier = Modifier.weight(1f))

                        OutlinedButton(onClick = {
                            onSaveServicio()
                            volverALista()
                        }) {
                            Text(text = "Guardar")
                            Icon(Icons.Filled.Add, "Guardar")
                        }

                        Spacer(modifier = Modifier.weight(0.1f))


                        if (uiState.servicioId != null && uiState.servicioId != 0) {
                            OutlinedButton(onClick = {
                                onDeleteServicio()
                                volverALista()
                            }) {
                                Text(text = "Borrar")
                                Icon(Icons.Filled.Delete, "Borrar")
                            }
                        }


                        Spacer(modifier = Modifier.weight(0.1f))

                        OutlinedButton(onClick = { limpiarServicio() }) {
                            Text(text = "Limpiar")
                            Icon(Icons.Filled.Clear, "Limpiar")
                        }
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun PreviewBody() {

}