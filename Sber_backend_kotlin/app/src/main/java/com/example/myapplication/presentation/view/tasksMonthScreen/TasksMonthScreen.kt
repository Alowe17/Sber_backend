package com.example.myapplication.presentation.view.tasksMonthScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.presentation.viewmodel.TasksMonthUiState
import com.example.myapplication.presentation.viewmodel.TasksMonthViewModel
import com.example.myapplication.ui.theme.*

data class MonthlyTask(
    val title: String,
    val progress: Float,
    val current: String,
    val target: String,
    val deadline: String,
    val rewardPoints: Int,
    val icon: ImageVector
)

@Composable
fun TasksMonthScreen(viewModel: TasksMonthViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) { viewModel.loadTasks() }

    val backgroundGradient = Brush.linearGradient(
        colors = listOf(Color(0xFFACCBB7), Color(0xFFDCEADD), Color(0xFFF2F5F3)),
        start = Offset(0f, 0f),
        end = Offset.Infinite
    )

    val tasks = when (val state = uiState) {
        is TasksMonthUiState.Success -> state.tasks.map { et ->
            val t = et.task ?: return@map null
            val progress = if (t.targetValue > 0) et.progress.toFloat() / t.targetValue else 0f
            MonthlyTask(
                title = t.title ?: "",
                progress = progress.coerceIn(0f, 1f),
                current = et.progress.toString(),
                target = "${t.targetValue}",
                deadline = t.deadline ?: "—",
                rewardPoints = t.rewardPoint,
                icon = Icons.Default.TaskAlt
            )
        }.filterNotNull()
        else -> emptyList()
    }

    when (uiState) {
        is TasksMonthUiState.Loading -> Box(
            modifier = Modifier.fillMaxSize().background(backgroundGradient),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = SberGreen)
        }
        is TasksMonthUiState.Error -> Box(
            modifier = Modifier.fillMaxSize().background(backgroundGradient),
            contentAlignment = Alignment.Center
        ) {
            Text((uiState as TasksMonthUiState.Error).message, color = Color.Red)
        }
        else -> TasksMonthContent(tasks, backgroundGradient)
    }
}

@Composable
private fun TasksMonthContent(
    tasks: List<MonthlyTask>,
    backgroundGradient: androidx.compose.ui.graphics.Brush
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGradient)
    ) {
        // Заголовок
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 16.dp, start = 20.dp, end = 20.dp)
        ) {
            Text(
                text = "Задачи месяца",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = SberBlack
            )
            Text(
                text = "Выполняйте цели, чтобы получить бонусные баллы к рейтингу",
                fontSize = 14.sp,
                color = TextSecondaryGray,
                lineHeight = 18.sp
            )
        }

        // Список задач
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(tasks) { task ->
                TaskCard(task)
            }
        }
    }
}

@Composable
fun TaskCard(task: MonthlyTask) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Верхняя часть: Иконка + Название + Награда
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Surface(
                        modifier = Modifier.size(40.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = SberGreen.copy(alpha = 0.1f)
                    ) {
                        Icon(
                            imageVector = task.icon,
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp),
                            tint = SberGreen
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = task.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = SberBlack
                    )
                }

                // Награда
                Surface(
                    color = SberGreen,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "+${task.rewardPoints} балла",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Прогресс-бар и текущие значения
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Прогресс: ${task.current}",
                    fontSize = 13.sp,
                    color = SberBlack,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Цель: ${task.target}",
                    fontSize = 13.sp,
                    color = TextSecondaryGray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { task.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = SberGreen,
                trackColor = DividerGray.copy(alpha = 0.3f),
                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Дедлайн
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Event,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = TextSecondaryGray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Срок: ${task.deadline}",
                    fontSize = 12.sp,
                    color = TextSecondaryGray
                )
            }
        }
    }
}