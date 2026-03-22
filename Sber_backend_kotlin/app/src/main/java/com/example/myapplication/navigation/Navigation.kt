package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.presentation.view.currentStatusScreen.CurrentStatusScreen
import com.example.myapplication.presentation.view.educationScreen.EducationScreen
import com.example.myapplication.presentation.view.loginScreen.LoginScreen
import com.example.myapplication.presentation.view.newsScreen.NewsScreen
import com.example.myapplication.presentation.view.personalFinancialEffectScreen.PersonalFinancialEffectScreen
import com.example.myapplication.presentation.view.privilegesScreen.PrivilegesScreen
import com.example.myapplication.presentation.view.ratingDetailsScreen.RatingDetailsScreen
import com.example.myapplication.presentation.view.resultsDayScreen.ResultsDayScreen
import com.example.myapplication.presentation.view.scenarioCalculatorScreen.ScenarioCalculatorScreen
import com.example.myapplication.presentation.view.profileScreen.ProfileScreen
import com.example.myapplication.presentation.view.ratingScreen.RatingScreen
import com.example.myapplication.presentation.view.supportScreen.SupportScreen
import com.example.myapplication.presentation.view.tasksMonthScreen.TasksMonthScreen

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Status.route) {
            CurrentStatusScreen(navController)
        }

        composable(Screen.Details.route) {
            RatingDetailsScreen(navController)
        }

        composable(Screen.Calculator.route) {
            ScenarioCalculatorScreen(navController)
        }

        composable(Screen.Privilege.route) {
            PrivilegesScreen(navController)
        }

        composable(Screen.FinancialEffect.route) {
            PersonalFinancialEffectScreen(navController)
        }

        composable(Screen.ResultDay.route) {
            ResultsDayScreen(navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.Tasks.route){
            TasksMonthScreen()
        }

        composable(Screen.Rating.route) {
            RatingScreen()
        }

        composable(Screen.Education.route) {
            EducationScreen(navController)
        }

        composable(Screen.Support.route){
            SupportScreen(navController)
        }

        composable(Screen.News.route){
            NewsScreen(navController)
        }
    }
}