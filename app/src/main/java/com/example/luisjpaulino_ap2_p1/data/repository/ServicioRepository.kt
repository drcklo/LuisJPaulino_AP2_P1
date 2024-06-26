package com.example.luisjpaulino_ap2_p1.data.repository

import android.util.Log
import com.example.luisjpaulino_ap2_p1.data.remote.ServiciosApi
import com.example.luisjpaulino_ap2_p1.data.remote.dto.ServicioDto
import com.example.luisjpaulino_ap2_p1.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ServicioRepository @Inject constructor (
    private val serviciosApi: ServiciosApi
) {
    fun getServicios(): Flow<Resource<List<ServicioDto>>> = flow {
        emit(Resource.Loading())
        try {
            val servicios = serviciosApi.getServicios()
            emit(Resource.Success(servicios))
        } catch (e: Exception) {
            Log.e("ServicioRepository", "getServicios: ${e.message}")
            emit(Resource.Error(e.message ?: "An unexpected error has occured"))
        }
    }
    suspend fun getServicio(id: Int): ServicioDto? {
        return try {
            serviciosApi.getServicio(id)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun addServicio(servicio: ServicioDto) {
        serviciosApi.addServicios(servicio)
    }

    suspend fun updateServicio(servicio: ServicioDto) {
        serviciosApi.updateServicio(servicio.servicioId, servicio)
    }

    suspend fun deleteServicio(id: Int) {
        serviciosApi.deleteServicio(id)
    }
}