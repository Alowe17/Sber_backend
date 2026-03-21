package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                ActionPlanScreen()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Details.route
    ) {
        // Экран 1: Статус
        composable(Screen.Status.route) {
            CurrentStatusScreen(
                onNavigateToCalc = { navController.navigate(Screen.Calculator.route) }
            )
        }

        // Экран 2: Детализация
        composable(Screen.Details.route) {
            RatingDetailsScreen(
                onNavigateToCalc = {navController.navigate(Screen.Calculator.route)}
            )
        }

        // Экран 3: Калькулятор
        composable(Screen.Calculator.route) {
            ScenarioCalculatorScreen()
        }
    }
}