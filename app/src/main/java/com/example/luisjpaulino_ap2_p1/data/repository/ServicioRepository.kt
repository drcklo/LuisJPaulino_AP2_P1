package com.example.luisjpaulino_ap2_p1.data.repository

import com.example.luisjpaulino_ap2_p1.data.local.dao.ServicioDao
import com.example.luisjpaulino_ap2_p1.data.local.entities.ServicioEntity

class ServicioRepository(private val servicioDao: ServicioDao) {
    suspend fun saveServicio(servicio: ServicioEntity) = servicioDao.save(servicio)
    suspend fun deleteServicio(servicio: ServicioEntity) = servicioDao.delete(servicio)
    suspend fun getServicio(id: Int) = servicioDao.find(id)
    fun getServicios() = servicioDao.getAll()
}