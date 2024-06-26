package com.example.luisjpaulino_ap2_p1.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luisjpaulino_ap2_p1.data.remote.dto.ServicioDto
import com.example.luisjpaulino_ap2_p1.data.repository.ServicioRepository
import com.example.luisjpaulino_ap2_p1.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServicioViewModel @Inject constructor (private val repository: ServicioRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ServicioUIState())
    val uiState = _uiState.asStateFlow()
    private val servicioId: Int = 0
    fun getServicios(){
        repository.getServicios().onEach { result ->
            when (result) {
                is Resource.Loading -> _uiState.update {
                    it.copy(isLoading = true)
                }
                is Resource.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        servicios = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    init {
        viewModelScope.launch {
            getServicios()
        }
    }

    fun getServicio() {
        viewModelScope.launch {
            val servicio = repository.getServicio(servicioId)
            servicio?.let {
                _uiState.update {
                    it.copy(
                        servicioId = servicio.servicioId,
                        descripcion = servicio.descripcion,
                        precio = servicio.precio
                    )
                }
            }
        }
    }

    fun saveServicio() {
        viewModelScope.launch {
            try {
                if (uiState.value.servicioId != null) {
                    repository.updateServicio(uiState.value.toEntity())
                } else {
                    repository.addServicio(uiState.value.toEntity())
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun limpiarServicio() {
        viewModelScope.launch {
            _uiState.value = ServicioUIState()
        }
    }

    fun borrarServicio() {
        viewModelScope.launch {
            repository.deleteServicio(uiState.value.servicioId ?: 0)
        }
    }

    fun onDescripcionChange(descripcion: String) {

        val regex = Regex("^[a-zA-Z0-9 ]*\$")

        val errorDescripcion: String? = when {
            descripcion.isEmpty() -> "La descripción no puede estar vacía"
            !descripcion.matches(regex) -> "La descripción solo puede contener letras y números"
            else -> null
        }

        _uiState.update {
            it.copy(
                descripcion = descripcion, errorDescripcion = errorDescripcion
            )
        }
    }

    fun onPrecioChange(precio: String) {

        val regex = Regex("^\\d*\\.?\\d*\$")

        val precio_ = precio.toDoubleOrNull()

        _uiState.update {
            it.copy(
                precio = precio_,
                errorPrecio = if (precio_ != null && precio.matches(regex)) null else "El precio solo puede contener números"
            )
        }
    }

    fun validation(): Boolean {
        return false
    }

    data class ServicioUIState(
        val servicioId: Int? = null,
        var descripcion: String? = "",
        var precio: Double? = 0.0,
        var errorDescripcion: String? = null,
        var errorPrecio: String? = null,
        val servicios: List<ServicioDto> = emptyList(),
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    fun ServicioUIState.toEntity() = ServicioDto(
        servicioId = servicioId ?: 0,
        descripcion = descripcion ?: "",
        precio = precio ?: 0.0
    )
}
