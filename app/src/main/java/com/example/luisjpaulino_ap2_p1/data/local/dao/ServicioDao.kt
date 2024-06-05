package com.example.luisjpaulino_ap2_p1.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.luisjpaulino_ap2_p1.data.local.entities.ServicioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicioDao {
    @Upsert
    suspend fun save(servicio: ServicioEntity)
    @Delete
    suspend fun delete(servicio: ServicioEntity)
    @Query(
        """
            SELECT * 
            FROM Servicios
            WHERE servicioId=:id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): ServicioEntity?
    @Query("SELECT * FROM Servicios")
    fun getAll(): Flow<List<ServicioEntity>>
}