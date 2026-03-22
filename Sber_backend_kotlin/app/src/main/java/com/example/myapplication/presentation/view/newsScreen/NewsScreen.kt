package com.example.myapplication.presentation.view.newsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.*

// Категории новостей для фильтрации или цветовой индикации
enum class NewsCategory(val label: String, val color: Color, val icon: ImageVector) {
    TARIFF("Тарифы", Color(0xFF2196F3), Icons.Default.Payments),
    UPDATE("Изменения", Color(0xFFFF9800), Icons.Default.NewReleases),
    NEWS("События", Color(0xFF4CAF50), Icons.Default.Campaign),
    DOCUMENT("Документы", Color(0xFF9C27B0), Icons.Default.Description)
}

data class NewsItem(
    val title: String,
    val description: String,
    val date: String,
    val category: NewsCategory
)

@Composable
fun NewsScreen(navController: NavHostController) {
    val newsList = listOf(
        NewsItem(
            "Обновление тарифов ипотеки",
            "С 25 марта вступают в силу новые процентные ставки по программе «Дом клик». Ознакомьтесь с изменениями в регламенте.",
            "Сегодня, 10:30",
            NewsCategory.TARIFF
        ),
        NewsItem(
            "Изменение логики начисления баллов",
            "Теперь за продажу доп. продуктов категории 'Страхование+' начисляется в 1.5 раза больше баллов рейтинга.",
            "Вчера, 14:20",
            NewsCategory.UPDATE
        ),
        NewsItem(
            "Весенний марафон КСО",
            "Запускаем соревнование между дилерскими центрами. Главный приз — поездка на конференцию в Сочи.",
            "20 марта",
            NewsCategory.NEWS
        )
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
        // Шапка
        Column(modifier = Modifier.padding(top = 48.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)) {
            Text(
                text = "Новости",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = SberBlack
            )
            Text(
                text = "Будьте в курсе последних изменений и тарифов",
                fontSize = 14.sp,
                color = TextSecondaryGray
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(newsList) { news ->
                NewsCard(news)
            }
        }
    }
}

@Composable
fun NewsCard(news: NewsItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Категория и Дата
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = news.category.color.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = news.category.icon,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = news.category.color
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = news.category.label,
                            color = news.category.color,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Text(
                    text = news.date,
                    fontSize = 12.sp,
                    color = TextSecondaryGray
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Заголовок
            Text(
                text = news.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = SberBlack,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Текст новости
            Text(
                text = news.description,
                fontSize = 14.sp,
                color = SberBlack.copy(alpha = 0.8f),
                lineHeight = 20.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка "Подробнее"
            TextButton(
                onClick = { /* Открыть новость полностью */ },
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.height(30.dp)
            ) {
                Text(
                    text = "Читать далее →",
                    color = SberGreen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}