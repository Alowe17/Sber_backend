package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.network.ApiClient
import com.example.myapplication.network.ApiConfig
import com.example.myapplication.network.StatusResponse
import com.example.myapplication.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun CurrentStatusScreen(
    onNavigateToCalc: () -> Unit
) {
    var status by remember { mutableStateOf<StatusResponse?>(null) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        loading = true
        error = null
        runCatching {
            withContext(Dispatchers.IO) {
                ApiClient.api.getCurrentStatus(ApiConfig.USER_ID)
            }
        }.onSuccess {
            status = it
        }.onFailure {
            error = "Не удалось загрузить статус"
        }
        loading = false
    }

    val level = status?.currentLevel ?: "UNKNOWN"
    val nextLevel = status?.nextLevel ?: "NEXT"
    val pointsToNext = status?.pointsToNext ?: 0
    val progressPercent = (status?.progressPercent ?: 0).coerceIn(0, 100)
    val progress = progressPercent / 100f

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

        StatusBadge(level = level)

        Spacer(modifier = Modifier.height(32.dp))

        // Карточка прогресса
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundLightGray)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "До $nextLevel осталось $pointsToNext баллов",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    color = SberGreen,
                    trackColor = DividerGray
                )

                Text(
                    text = "$progressPercent%",
                    modifier = Modifier.align(Alignment.End),
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryGray
                )
            }
        }
        if (loading) {
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        if (error != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = error!!, color = WarningOrange)
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
                    text = "При переходе на $nextLevel ваш годовой доход вырастет",
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