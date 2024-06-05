package com.example.luisjpaulino_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.luisjpaulino_ap2_p1.data.local.database.ServicioDb
import com.example.luisjpaulino_ap2_p1.data.repository.ServicioRepository
import com.example.luisjpaulino_ap2_p1.presentation.navigation.ParcialNavHost
import com.example.luisjpaulino_ap2_p1.ui.theme.LuisJPaulino_AP2_P1Theme

class MainActivity : ComponentActivity() {
    private lateinit var servicioDb: ServicioDb
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        servicioDb = Room.databaseBuilder(
            this,
            ServicioDb::class.java,
            "Servicio.db"
        )
            .fallbackToDestructiveMigration()
            .build()

        val repository = ServicioRepository(servicioDb.servicioDao())

        enableEdgeToEdge()
        setContent {
            LuisJPaulino_AP2_P1Theme {
                    val navHost = rememberNavController()
                    ParcialNavHost(navHost, repository)
            }
        }
    }
}
