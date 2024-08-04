package com.weatherapp.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

// Uma classe sealed não pode ser estendida (com subclasses) fora do contexto dessa aplicação.
sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String) {
    
    // data object são objetos que o método toString() foi sobrescrito para retornar o nome
    data object HomePage : BottomNavItem("Inicio", Icons.Default.Home, "home")
    data object ListPage : BottomNavItem("Lista", Icons.Default.Favorite, "list")
    data object MapPage : BottomNavItem("Mapa", Icons.Default.LocationOn, "map")
}