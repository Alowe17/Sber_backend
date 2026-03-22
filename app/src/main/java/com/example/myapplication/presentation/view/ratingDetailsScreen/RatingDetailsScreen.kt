package com.example.myapplication.presentation.view.ratingDetailsScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.*
import com.example.myapplication.R
import com.example.myapplication.navigation.Screen

@Composable
fun RatingDetailsScreen(
    navHostController: NavHostController
) {
    // Тот самый темный градиент от угла
    val diagonalGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFACCBB7), // Темный угол
            Color(0xFFDCEADD), // Середина
            Color(0xFFF2F5F3)  // Светлый угол
        ),
        start = Offset(0f, 0f),
        end = Offset.Infinite
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(diagonalGradient)
    ) {
        // --- СТАТИЧНАЯ ШАПКА С ЛОГО ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 40.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Детализация рейтинга",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = SberBlack
            )

            Image(
                painter = painterResource(id = R.drawable.sber_logo),
                contentDescription = "Sber Logo",
                modifier = Modifier.height(28.dp),
                contentScale = ContentScale.Fit
            )
        }

        // --- ПРОКРУЧИВАЕМЫЙ СПИСОК ---
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                MetricAnalysisCard(
                    title = "Объём",
                    points = "32 балла",
                    calculation = "1 млн ₽ профинансированного объема = 2 балла",
                    howToIncrease = "Закройте текущие заявки в CRM до конца недели."
                )
            }

            item {
                MetricAnalysisCard(
                    title = "Сделки",
                    points = "18 баллов",
                    calculation = "1 выданный кредит = 1.5 балла",
                    howToIncrease = "Увеличьте количество консультаций на этапе выбора авто."
                )
            }

            item {
                MetricAnalysisCard(
                    title = "Доля банка",
                    points = "12 баллов",
                    calculation = "Каждые 10% доли Сбера в портфеле = 4 балла",
                    howToIncrease = "Предлагайте продукты Сбера как приоритетные."
                )
            }
        }

        // --- СТАТИЧНАЯ КНОПКА ВНИЗУ ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Button(
                onClick = { navHostController.navigate(Screen.Calculator.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SberGreen),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = "Смоделировать рост",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = SberWhite
                )
            }
        }
    }
}

@Composable
fun MetricAnalysisCard(
    title: String,
    points: String,
    calculation: String,
    howToIncrease: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        // Сделал карточки чуть прозрачными, чтобы градиент слегка просвечивал
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = SberBlack)
                Surface(
                    color = SberGreen,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = points,
                        color = SberWhite,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = DividerGray.copy(alpha = 0.5f)
            )

            InfoRow(
                icon = Icons.Default.Functions,
                label = "Как рассчитывается",
                text = calculation
            )

            Spacer(modifier = Modifier.height(12.dp))

            InfoRow(
                icon = Icons.AutoMirrored.Filled.TrendingUp,
                label = "Как увеличить",
                text = howToIncrease
            )
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, text: String) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = SberGreen,
            modifier = Modifier
                .size(18.dp)
                .padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = label, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = SberBlack)
            Text(text = text, fontSize = 12.sp, color = TextSecondaryGray, lineHeight = 16.sp)
        }
    }
}