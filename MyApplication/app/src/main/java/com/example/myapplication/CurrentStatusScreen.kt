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
import com.example.myapplication.model.StatusDto
import com.example.myapplication.network.ApiService
import com.example.myapplication.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun CurrentStatusScreen(
    onNavigateToCalc: () -> Unit
) {
    var status by remember { mutableStateOf<StatusDto?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        try {
            status = ApiClient.api.getStatus(1L)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    if (status == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = SberGreen)
        }
        return
    }

    val data = status!!

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

        StatusBadge(level = data.currentLevel)

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundLightGray)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text(
                    text = "До ${data.nextLevel} осталось ${data.pointsToNext} баллов",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                LinearProgressIndicator(
                    progress = { data.progressPercent / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    color = SberGreen,
                    trackColor = DividerGray
                )

                Text(
                    text = "${data.progressPercent}%",
                    modifier = Modifier.align(Alignment.End),
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondaryGray
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

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
                    text = "При переходе на ${data.nextLevel} доход вырастет",
                    style = MaterialTheme.typography.bodyMedium
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