package com.example.luisjpaulino_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.luisjpaulino_ap2_p1.presentation.ListScreen

@Composable
fun ParcialNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.List
    ) {
        composable<Screen.List>{
            ListScreen()
        }
        composable<Screen.Registro>{
            //Aqui va el componente registro :)
        }
    }
}