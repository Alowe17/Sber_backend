package com.example.myapplication.navigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.*;
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector? = null // Рекомендую использовать один ресурс или пару active/inactive
) {
    // Приоритет №1: Основные рабочие экраны [cite: 112]
    object Status : Screen("status_screen", "Статус", Icons.Default.Home)
    object Tasks : Screen("tasks_screen", "Задачи", Icons.AutoMirrored.Filled.ListAlt)
    object ResultDay : Screen("result_day_screen", "Итоги", Icons.Default.AddCircle)
    object Profile : Screen("profile_screen", "Профиль", Icons.Default.Person)

    // Остальные экраны (переход через кнопки или меню)
    object Details : Screen("details_screen", "Рейтинг")
    object Calculator : Screen("calculator_screen", "Калькулятор")
    object Privilege : Screen("privilege_screen", "Привилегии")
    object FinancialEffect : Screen("financial_effect_screen", "Выгода")
    object Rating : Screen("rating_screen", "Топ-10")
    object Education : Screen("education_screen", "Обучение")
    object Support : Screen("support_screen", "Поддержка")
    object News : Screen("news_screen", "Новости")
    object Login : Screen("login_screen", "Вход")
}