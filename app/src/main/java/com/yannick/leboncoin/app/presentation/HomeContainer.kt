package com.yannick.leboncoin.app.presentation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yannick.leboncoin.base.presentation.compose.HomeScreen
import com.yannick.leboncoin.feature.home.presentation.screen.home.HomePageContainer

@Composable
fun HomeContainer() {
    val navController = rememberNavController()
    HomeContainerNavGraph(navController)
}

@Composable
fun HomeContainerNavGraph(
    navController: NavHostController,
) {
    return NavHost(
        navController = navController,
        startDestination = HomeScreen,
    ) {
        composable(
            route = HomeScreen,
            enterTransition = { slideInHorizontally(animationSpec = tween(500)) },
            exitTransition = { slideOutHorizontally(animationSpec = tween(500)) },
        ) {
            HomePageContainer()
        }
    }
}
