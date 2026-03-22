package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CastForEducation
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.*

data class GrowthAction(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val pointsReward: String,
    val category: String
)

@Composable
fun ActionPlanScreen() {
    val actions = listOf(
        GrowthAction(
            "Увеличить долю Сбера",
            "Предложите КАСКО от Сбера следующим 3 клиентам, чтобы поднять долю до 40%.",
            Icons.Default.RocketLaunch,
            "+12 баллов",
            "Приоритет"
        ),
        GrowthAction(
            "Закрыть крупную сделку",
            "Оформите финансирование на сумму от 3 млн ₽ для достижения цели по объему.",
            Icons.Default.Assignment,
            "+8 баллов",
            "Объем"
        ),
        GrowthAction(
            "Курс: Эффективные продажи",
            "Пройдите микро-обучение (5 мин) по работе с возражениями в SberKnowledge.",
            Icons.Default.CastForEducation,
            "+2 балла",
            "Обучение"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SberWhite)
            .padding(16.dp, 30.dp, 16.dp, 16.dp)
    ) {
        // Заголовок
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Lightbulb, contentDescription = null, tint = WarningOrange)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Как вырасти до Gold",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = SberBlack
            )
        }

        Text(
            text = "Персональные рекомендации на основе вашей статистики",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryGray,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Список действий
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(actions) { action ->
                ActionCard(action)
            }
        }

        // Финальный призыв к действию
        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundSuccessGreen),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Выполнение этих действий принесет вам переход на новый уровень уже через 4 дня",
                    style = MaterialTheme.typography.bodySmall,
                    color = SberGreen,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun ActionCard(action: GrowthAction) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BackgroundLightGray),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Иконка в круге
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                color = SberWhite
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(action.icon, contentDescription = null, tint = SberGreen)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = action.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = SberGreen,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = action.pointsReward,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = SberBlack
                    )
                }

                Text(
                    text = action.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = SberBlack,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Text(
                    text = action.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondaryGray,
                    lineHeight = 20.sp
                )

                Button(
                    onClick = { /* Выполнить / Перейти */ },
                    modifier = Modifier.padding(top = 12.dp).height(36.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SberGreen),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Приступить", fontSize = 12.sp, color = SberWhite)
                }
            }
        }
    }
}