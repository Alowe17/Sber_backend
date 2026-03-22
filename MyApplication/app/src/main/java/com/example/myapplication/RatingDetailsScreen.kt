package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.RatingDetailDto
import com.example.myapplication.network.ApiClient
import com.example.myapplication.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun RatingDetailsScreen(
    onNavigateToCalc: () -> Unit
) {
    var details by remember { mutableStateOf<List<RatingDetailDto>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        loading = true
        error = null
        runCatching {
            withContext(Dispatchers.IO) {
                ApiClient.api.getRatingDetails(1L)
            }
        }.onSuccess {
            details = it
        }.onFailure {
            error = "Не удалось загрузить детализацию"
        }
        loading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SberWhite)
            .padding(16.dp, 30.dp, 16.dp, 16.dp)
    ) {
        // Шапка экрана
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Детализация",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = SberBlack
                )
                Text(text = if (loading) "Обновление..." else "Данные из бэкенда", style = MaterialTheme.typography.bodySmall, color = TextSecondaryGray)
            }
            // Иконка инфо (согласно стилистике Сбера)
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = SberGreen
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Основные показатели в виде списка
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            if (details.isEmpty() && !loading) {
                item {
                    Text("Данных пока нет", color = TextSecondaryGray)
                }
            } else {
                items(details.size) { index ->
                    val item = details[index]
                    DetailedMetricCard(
                        label = mapTypeToTitle(item.type),
                        currentValue = "${item.points}",
                        targetValue = "баллов",
                        points = "+${item.points} баллов",
                        progress = (item.points.coerceAtMost(100).toFloat() / 100f),
                        accentColor = if (item.points >= 10) SberGreen else WarningOrange
                    )
                }
            }
            if (error != null) {
                item {
                    Text(error!!, color = WarningOrange)
                }
            }
        }

        OutlinedButton(
            onClick = { onNavigateToCalc() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, SberGreen)
        ) {
            Text(
                text = "Смоделировать рост",
                color = SberGreen,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun mapTypeToTitle(type: String): String = when (type) {
    "NEW_DEAL" -> "Новые сделки"
    "MARKET_SHARE" -> "Доля банка"
    "BONUS" -> "Бонусы"
    else -> type
}

@Composable
fun DetailedMetricCard(
    label: String,
    currentValue: String,
    targetValue: String,
    points: String,
    progress: Float,
    accentColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BackgroundLightGray),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = label, fontWeight = FontWeight.SemiBold, color = SberBlack)
                Text(text = points, fontWeight = FontWeight.Bold, color = accentColor)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(text = currentValue, fontSize = 20.sp, fontWeight = FontWeight.Black, color = SberBlack)
                Text(text = "Цель: $targetValue", fontSize = 12.sp, color = TextSecondaryGray)
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = accentColor,
                trackColor = DividerGray
            )
        }
    }
}