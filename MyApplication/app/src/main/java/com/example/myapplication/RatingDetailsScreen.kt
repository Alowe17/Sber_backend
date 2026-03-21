package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.*

@Composable
fun RatingDetailsScreen(
    onNavigateToCalc: () -> Unit
) {
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
                Text(
                    text = "Обновлено: сегодня, 09:00",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryGray
                )
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
            // Секция 1: Объём сделок
            item {
                DetailedMetricCard(
                    label = "Объём сделок",
                    currentValue = "15.2 млн ₽",
                    targetValue = "20 млн ₽",
                    points = "+30 баллов",
                    progress = 0.76f,
                    accentColor = SberGreen
                )
            }

            // Секция 2: Количество сделок
            item {
                DetailedMetricCard(
                    label = "Количество сделок",
                    currentValue = "12 шт",
                    targetValue = "15 шт",
                    points = "+18 баллов",
                    progress = 0.8f,
                    accentColor = SberGreen
                )
            }

            // Секция 3: Доля банка (Критический показатель)
            item {
                DetailedMetricCard(
                    label = "Доля банка",
                    currentValue = "32%",
                    targetValue = "45%",
                    points = "+5 баллов",
                    progress = 0.4f,
                    accentColor = WarningOrange // Оранжевый, если показатель требует внимания
                )
            }

            // Секция 4: Ежедневный бонус за активность
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = BackgroundSuccessGreen),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = SberGreen)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Динамика рейтинга выше на 12%, чем вчера",
                            style = MaterialTheme.typography.bodyMedium,
                            color = SberGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
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