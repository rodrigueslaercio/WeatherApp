package com.weatherapp.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.weatherapp.ui.HomePage
import com.weatherapp.ui.ListPage
import com.weatherapp.ui.MainViewModel
import com.weatherapp.ui.MapPage

//NavHost é a @composable function onde as páginas da BottomNavBar serão exibidas
@Composable
fun MainNavHost(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController , startDestination = BottomNavItem.HomePage.route) {
        // composable (route = NOME DESTA DESTINAÇÃO) { UI DA DESTINAÇÃO }
        composable(route = BottomNavItem.HomePage.route) {
            HomePage()
        }
        composable(route = BottomNavItem.ListPage.route) {
            ListPage(viewModel = viewModel)
        }
        composable(route = BottomNavItem.MapPage.route) {
            MapPage()
        }
    }
}