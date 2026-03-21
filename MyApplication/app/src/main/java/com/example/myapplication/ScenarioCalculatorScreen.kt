package com.example.myapplication

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.network.ApiClient
import com.example.myapplication.network.ApiConfig
import com.example.myapplication.network.DailyResultRequest
import com.example.myapplication.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ScenarioCalculatorScreen() {
    val scope = rememberCoroutineScope()
    var saveMessage by remember { mutableStateOf<String?>(null) }
    // Состояния для параметров из ТЗ (стр. 3)
    var dealsCount by remember { mutableStateOf(10f) }      // Кол-во сделок
    var loanVolume by remember { mutableStateOf(15_000_000f) } // Объем в рублях
    var bankShare by remember { mutableStateOf(40f) }       // Доля Сбера %

    // Упрощенная логика рейтинга (для демонстрации)
    val totalPoints = (dealsCount * 2) + (loanVolume / 1_000_000 * 1.5f) + (bankShare * 0.4f)
    val isGold = totalPoints >= 60

    // Анимация цвета фона карточки в зависимости от статуса
    val cardBg by animateColorAsState(
        if (isGold) BackgroundSuccessGreen else BackgroundLightGray
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 30.dp, 16.dp, 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Сценарный калькулятор",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = SberBlack
        )
        Text(
            text = "Моделируйте свои показатели для перехода на новый уровень",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondaryGray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Блок управления параметрами
        CalculatorCard(title = "Параметры месяца") {
            // Метрика 1: Количество сделок
            ParameterSlider(
                label = "Количество сделок",
                value = "${dealsCount.toInt()} шт",
                currentValue = dealsCount,
                range = 0f..50f,
                onValueChange = { dealsCount = it }
            )

            // Метрика 2: Объем профинансированных сделок
            ParameterSlider(
                label = "Объем сделок",
                value = "${(loanVolume / 1_000_000).toInt()} млн ₽",
                currentValue = loanVolume,
                range = 0f..50_000_000f,
                onValueChange = { loanVolume = it }
            )

            // Метрика 3: Доля банка
            ParameterSlider(
                label = "Доля Сбера",
                value = "${bankShare.toInt()}%",
                currentValue = bankShare,
                range = 0f..100f,
                onValueChange = { bankShare = it }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Блок результата баллов (меняет цвет на SberGreen при достижении цели)
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (isGold) SberGreen else SberBlack
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "ПРОГНОЗ РЕЙТИНГА",
                    color = SberWhite.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
                Text(
                    "${totalPoints.toInt()} баллов",
                    color = SberWhite,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    text = if (isGold) "Уровень GOLD достигнут!" else "До уровня GOLD: ${(60 - totalPoints).toInt()} баллов",
                    color = SberWhite
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Ваша выгода при этом сценарии:", fontWeight = FontWeight.Bold, color = SberBlack)
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BenefitSmallCard("Доп. доход", "+45 000 ₽", Modifier.weight(1f))
            BenefitSmallCard("ДМС", "Софинанс.", Modifier.weight(1f))
            BenefitSmallCard("Ипотека", "-2%", Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Финальная кнопка
        Button(
            onClick = {
                scope.launch {
                    val request = DailyResultRequest(
                        dealsCount = dealsCount.toInt(),
                        volumeAmount = loanVolume.toDouble(),
                        productsCount = (bankShare / 10f).toInt()
                    )
                    runCatching {
                        withContext(Dispatchers.IO) {
                            ApiClient.api.saveDailyResult(ApiConfig.USER_ID, request)
                        }
                    }.onSuccess {
                        saveMessage = "Сценарий отправлен в бэкенд"
                    }.onFailure {
                        saveMessage = "Ошибка отправки на сервер"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SberGreen),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Сделать моей целью на месяц", fontSize = 16.sp, color = SberWhite)
        }
        if (saveMessage != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = saveMessage!!, color = TextSecondaryGray)
        }
    }
}

@Composable
fun ParameterSlider(
    label: String,
    value: String,
    currentValue: Float,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 10.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = SberBlack)
            Text(value, fontWeight = FontWeight.Bold, color = SberGreen)
        }
        Slider(
            value = currentValue,
            onValueChange = onValueChange,
            valueRange = range,
            colors = SliderDefaults.colors(
                thumbColor = SberGreen,
                activeTrackColor = SberGreen,
                inactiveTrackColor = DividerGray
            )
        )
    }
}

@Composable
fun CalculatorCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BackgroundLightGray),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp), content = content)
    }
}

@Composable
fun BenefitSmallCard(title: String, value: String, modifier: Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = SberWhite),
        border = androidx.compose.foundation.BorderStroke(1.dp, DividerGray)
    ) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, fontSize = 10.sp, color = TextSecondaryGray)
            Text(value, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = SberBlack)
        }
    }
}