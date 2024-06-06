package com.example.luisjpaulino_ap2_p1.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luisjpaulino_ap2_p1.data.local.entities.ServicioEntity
import com.example.luisjpaulino_ap2_p1.data.repository.ServicioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ServicioViewModel(private val repository: ServicioRepository, private val servicioId: Int) :
    ViewModel() {

    var uiState = MutableStateFlow(ServicioUIState())
        private set

    val servicios = repository.getServicios().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    init {
        viewModelScope.launch {
            val servicio = repository.getServicio(servicioId)

            servicio?.let {
                uiState.update() {
                    it.copy(
                        servicioId = servicio.servicioId,
                        descripcion = servicio.descripcion ?: "",
                        precio = servicio.precio
                    )
                }
            }
        }
    }

    fun saveServicio() {
        viewModelScope.launch {
            repository.saveServicio(uiState.value.toEntity())
            limpiarServicio()
        }
    }

    fun limpiarServicio() {
        viewModelScope.launch {
            uiState.value = ServicioUIState()
        }
    }

    fun borrarServicio() {
        viewModelScope.launch {
            repository.deleteServicio(uiState.value.toEntity())
        }
    }

    fun onDescripcionChange(descripcion: String) {

        val regex = Regex("^[a-zA-Z0-9 ]*\$")

        val errorDescripcion: String? = when {
            descripcion.isEmpty() -> "La descripción no puede estar vacía"
            !descripcion.matches(regex) -> "La descripción solo puede contener letras y números"
            else -> null
        }

        if (descripcion.matches(regex)) {
            uiState.update {
                it.copy(
                    descripcion = descripcion, errorDescripcion = errorDescripcion
                )
            }
        }
    }

    fun onPrecioChange(precio: String) {

        val regex = Regex("/^\\d*\\.?\\d*\$/")

        val precio_ = precio.toDoubleOrNull()

        uiState.update {
            it.copy(
                precio = precio_,
                errorPrecio = if (!precio_.toString()
                        .matches(regex)
                ) null else "El precio solo puede contener números"
            )
        }
    }

    fun validation(): Boolean {
        return false
    }

}

data class ServicioUIState(
    val servicioId: Int? = null,
    var descripcion: String? = "",
    var precio: Double? = 0.0,
    var errorDescripcion: String? = null,
    var errorPrecio: String? = null
)

fun ServicioUIState.toEntity() = ServicioEntity(
    servicioId = servicioId, descripcion = descripcion, precio = precio
)