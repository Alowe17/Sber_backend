package com.example.myapplication.presentation.view.educationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OnDeviceTraining
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.*

// Модель данных для обучающего модуля
data class StudyModule(
    val title: String,
    val duration: String,
    val points: Int,
    val type: String, // "Видео", "Текст", "Тест"
    val icon: ImageVector,
    val isCompleted: Boolean = false
)

@Composable
fun EducationScreen(
    navController: NavHostController
) {
    val modules = listOf(
        StudyModule("Техники кросс-продаж", "15 мин", 10, "Видео", Icons.Default.PlayCircle),
        StudyModule("Работа с возражениями", "10 мин", 8, "Текст", Icons.Default.OnDeviceTraining),
        StudyModule("Итоговое тестирование", "20 мин", 25, "Тест", Icons.Default.Quiz),
        StudyModule("Новые скрипты 2026", "5 мин", 5, "Видео", Icons.Default.PlayCircle)
    )

    val backgroundGradient = Brush.linearGradient(
        colors = listOf(Color(0xFFACCBB7), Color(0xFFDCEADD), Color(0xFFF2F5F3)),
        start = Offset(0f, 0f),
        end = Offset.Infinite
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        // Заголовок
        Column(modifier = Modifier.padding(top = 48.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)) {
            Text(
                text = "Обучение",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = SberBlack
            )
            Text(
                text = "Повышайте навыки и зарабатывайте баллы",
                fontSize = 14.sp,
                color = TextSecondaryGray
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(modules) { module ->
                ModuleCard(module, onClick = { navController.popBackStack() })
            }
        }
    }
}

@Composable
fun ModuleCard(module: StudyModule, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка типа контента
            Surface(
                modifier = Modifier.size(52.dp),
                shape = RoundedCornerShape(14.dp),
                color = if (module.type == "Тест") Color(0xFFFFEB3B).copy(alpha = 0.2f) else SberGreen.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = module.icon,
                        contentDescription = null,
                        tint = if (module.type == "Тест") Color(0xFFFBC02D) else SberGreen,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = module.type.uppercase(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondaryGray,
                    letterSpacing = 1.sp
                )
                Text(
                    text = module.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = SberBlack
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Timer,
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = TextSecondaryGray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = module.duration, fontSize = 12.sp, color = TextSecondaryGray)
                }
            }

            // Баллы
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "+${module.points}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = SberGreen
                )
                Text(
                    text = "баллов",
                    fontSize = 10.sp,
                    color = SberGreen,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}