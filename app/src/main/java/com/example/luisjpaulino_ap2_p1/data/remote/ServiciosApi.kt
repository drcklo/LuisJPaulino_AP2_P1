package com.example.luisjpaulino_ap2_p1.data.remote

import com.example.luisjpaulino_ap2_p1.data.remote.dto.ServicioDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ServiciosApi {

    @GET("api/Servicios")
    suspend fun getServicios(): List<ServicioDto>
    @GET("api/Servicios/{id}")
    suspend fun getServicio(@Path("id") id: Int): ServicioDto
    @POST("api/Servicios")
    suspend fun addServicios(@Body servicioDto: ServicioDto?): Response<ServicioDto>
    @PUT("api/Servicios/{id}")
    suspend fun updateServicio(@Path("id") id: Int, @Body servicioDto: ServicioDto?): Response<ServicioDto>
    @DELETE("api/Servicios/{id}")
    suspend fun deleteServicio(@Path("id") id: Int): Response<Unit>
}