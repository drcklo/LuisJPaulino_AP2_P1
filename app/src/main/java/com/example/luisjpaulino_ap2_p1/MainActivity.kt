package com.example.luisjpaulino_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.luisjpaulino_ap2_p1.data.repository.ServicioRepository
import com.example.luisjpaulino_ap2_p1.presentation.navigation.ParcialNavHost
import com.example.luisjpaulino_ap2_p1.ui.theme.LuisJPaulino_AP2_P1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            LuisJPaulino_AP2_P1Theme {
                    val navHost = rememberNavController()
                    ParcialNavHost(navHost)
            }
        }
    }
}
