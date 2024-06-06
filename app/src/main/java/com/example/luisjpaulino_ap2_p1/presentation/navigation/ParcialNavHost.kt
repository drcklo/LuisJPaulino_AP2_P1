package com.example.luisjpaulino_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.luisjpaulino_ap2_p1.data.repository.ServicioRepository
import com.example.luisjpaulino_ap2_p1.presentation.ServicioListScreen
import com.example.luisjpaulino_ap2_p1.presentation.ServicioScreen
import com.example.luisjpaulino_ap2_p1.presentation.ServicioViewModel

@Composable
fun ParcialNavHost(navHostController: NavHostController, repository: ServicioRepository) {
    NavHost(
        navController = navHostController, startDestination = Screen.List
    ) {
        composable<Screen.List> {

            ServicioListScreen(viewModel = viewModel {
                ServicioViewModel(repository, 0)
            }, verSevicio = {
                navHostController.navigate(Screen.Registro(it.servicioId ?: 0))
            }, onAddServicio = {
                navHostController.navigate(Screen.Registro(0))
            })

        }
        composable<Screen.Registro> {
            val args = it.toRoute<Screen.Registro>()
            ServicioScreen(
                viewModel = viewModel {
                    ServicioViewModel(repository, args.xId)
                },
                volverALista = { navHostController.navigate(Screen.List) }
            )
        }
    }
}