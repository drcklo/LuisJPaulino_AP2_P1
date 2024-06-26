package com.example.luisjpaulino_ap2_p1.data.remote.dto

import com.squareup.moshi.Json

class ServicioDto (
    @Json(name = "ServicioId")
    val servicioId: Int,
    @Json(name = "Descripcion")
    val descripcion: String,
    @Json(name = "Precio")
    val precio: Double
)