package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.*

@Composable
fun CurrentStatusScreen(
    onNavigateToCalc: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 30.dp, 16.dp, 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ваш статус",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        StatusBadge(level = "Silver")

        Spacer(modifier = Modifier.height(32.dp))

        // Карточка прогресса
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundLightGray)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "До Gold осталось 12 баллов",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                LinearProgressIndicator(
                    progress = { 0.7f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    color = SberGreen,
                    trackColor = DividerGray
                )

                Text(
                    text = "70%",
                    modifier = Modifier.align(Alignment.End),
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryGray
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Карточка прогноза
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundSuccessGreen)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Финансовый прогноз",
                    style = MaterialTheme.typography.titleMedium,
                    color = SberGreen,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "При переходе на Gold ваш годовой доход вырастет на 180 000 ₽",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Экономия на ипотеке: 740 000 ₽",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onNavigateToCalc() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SberGreen)
        ) {
            Text("Как ускорить переход")
        }
    }
}

@Composable
fun StatusBadge(level: String) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .background(LevelSilver, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = level,
            color = SberWhite,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}