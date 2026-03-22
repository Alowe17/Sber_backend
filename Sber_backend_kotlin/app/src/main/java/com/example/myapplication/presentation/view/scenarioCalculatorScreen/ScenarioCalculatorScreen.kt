package com.example.myapplication.presentation.view.scenarioCalculatorScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.presentation.viewmodel.ScenarioCalculatorViewModel
import com.example.myapplication.presentation.viewmodel.ScenarioStatusState
import com.example.myapplication.ui.theme.*
import kotlin.math.roundToInt

@Composable
fun ScenarioCalculatorScreen(
    navHostController: NavHostController,
    viewModel: ScenarioCalculatorViewModel = viewModel()
) {
    val statusState by viewModel.statusState.collectAsState()
    LaunchedEffect(Unit) { viewModel.loadStatus() }

    var extraDeals by remember { mutableFloatStateOf(0f) }
    var extraVolume by remember { mutableFloatStateOf(0f) }
    var extraShare by remember { mutableFloatStateOf(0f) }
    var extraProducts by remember { mutableFloatStateOf(0f) }

    val basePoints = when (val s = statusState) {
        is ScenarioStatusState.Success -> s.status.currentPoints
        else -> 0
    }
    val currentLevelStr = when (val s = statusState) {
        is ScenarioStatusState.Success -> s.status.currentLevel
        else -> "SILVER"
    }
    when (statusState) {
        is ScenarioStatusState.Loading -> Box(
            modifier = Modifier.fillMaxSize().background(Brush.linearGradient(
                listOf(Color(0xFFACCBB7), Color(0xFFDCEADD), Color(0xFFF2F5F3))
            )),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = SberGreen)
        }
        is ScenarioStatusState.Error -> Box(
            modifier = Modifier.fillMaxSize().background(Brush.linearGradient(
                listOf(Color(0xFFACCBB7), Color(0xFFDCEADD), Color(0xFFF2F5F3))
            )),
            contentAlignment = Alignment.Center
        ) {
            Text((statusState as ScenarioStatusState.Error).message, color = Color.Red)
        }
        is ScenarioStatusState.Success -> CalculatorContent(
            basePoints,
            currentLevelStr,
            extraDeals, { extraDeals = it },
            extraVolume, { extraVolume = it },
            extraShare, { extraShare = it },
            extraProducts, { extraProducts = it },
            navHostController
        )
    }
}

@Composable
private fun CalculatorContent(
    basePoints: Int,
    currentLevelStr: String,
    extraDeals: Float, onDealsChange: (Float) -> Unit,
    extraVolume: Float, onVolumeChange: (Float) -> Unit,
    extraShare: Float, onShareChange: (Float) -> Unit,
    extraProducts: Float, onProductsChange: (Float) -> Unit,
    navHostController: NavHostController
) {
    val pointsFromDeals = (extraDeals * 1.5f).roundToInt()
    val pointsFromVolume = (extraVolume * 2f).roundToInt()
    val pointsFromShare = (extraShare / 5f * 4f).roundToInt()
    val pointsFromProducts = (extraProducts * 3f).roundToInt()
    val totalNewPoints = basePoints + pointsFromDeals + pointsFromVolume + pointsFromShare + pointsFromProducts
    val isGoldReached = totalNewPoints >= 100
    val commission = (extraDeals * 45000).roundToInt()
    val statusBonus = if (isGoldReached) 30000 else 0
    val totalIncome = commission + statusBonus

    val diagonalGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFACCBB7),
            Color(0xFFDCEADD),
            Color(0xFFF2F5F3)
        ),
        start = Offset(0f, 0f),
        end = Offset.Infinite
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(diagonalGradient)
    ) {
        // --- ШАПКА ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(top = 40.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Назад",
                        tint = SberBlack
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.Calculate, contentDescription = null, tint = SberGreen)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Калькулятор",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = SberBlack
                )
            }

            Image(
                painter = painterResource(id = R.drawable.sber_logo),
                contentDescription = "Sber Logo",
                modifier = Modifier
                    .padding(end = 16.dp)
                    .height(28.dp),
                contentScale = ContentScale.Fit
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Блок настройки (ЗАМЕНИЛИ БЕЛЫЙ НА СЕРЫЙ)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = BackgroundLightGray.copy(alpha = 0.8f) // Мягкий серый
                ),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // Убрали тень для плоского вида
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("Настройте сценарий роста:", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = SberBlack)
                    Spacer(modifier = Modifier.height(16.dp))

                    CalculatorSlider("Сделки", extraDeals, onDealsChange, 0f..10f, "шт")
                    CalculatorSlider("Объём", extraVolume, onVolumeChange, 0f..10f, "млн ₽")
                    CalculatorSlider("Доля банка", extraShare, onShareChange, 0f..50f, "%")
                    CalculatorSlider("Доп. продукты", extraProducts, onProductsChange, 0f..5f, "шт")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Ваш прогноз:", fontWeight = FontWeight.Bold, color = SberBlack, modifier = Modifier.padding(start = 8.dp))
            Spacer(modifier = Modifier.height(12.dp))

            // Блок результатов (ЗАМЕНИЛИ БЕЛЫЙ НА СЕРЫЙ)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = BackgroundLightGray.copy(alpha = 0.9f) // Более плотный серый
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    val highlightColor = if (isGoldReached) SberGreen else SberBlack

                    ResultRow("Итоговый рейтинг", "$totalNewPoints баллов", highlightColor)
                    ResultRow(
                        "Целевой уровень",
                        if (isGoldReached) "GOLD" else "SILVER",
                        if (isGoldReached) SberGreen else TextSecondaryGray
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = DividerGray.copy(alpha = 0.3f))

                    ResultRow("Ваш доход", "$totalIncome ₽", SberGreen)
                    ResultRow(
                        "Экономия на ипотеке",
                        if (isGoldReached) "740 000 ₽" else "Доступно на Gold",
                        if (isGoldReached) Color(0xFF007AFF) else TextSecondaryGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        // КНОПКА ВНИЗУ (Убрали белую подложку, если была)
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Button(
                onClick = { /* Фиксация */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isGoldReached) SberGreen else SberBlack
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.TrendingUp, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (isGoldReached) "Зафиксировать цель Gold" else "Смоделировать еще",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun CalculatorSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    unit: String
) {
    Column(modifier = Modifier.padding(vertical = 5.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.bodyMedium, color = SberBlack)
            Text(text = "${value.roundToInt()} $unit", fontWeight = FontWeight.Bold, color = SberGreen)
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
            colors = SliderDefaults.colors(
                thumbColor = SberGreen,
                activeTrackColor = SberGreen,
                inactiveTrackColor = DividerGray.copy(alpha = 0.5f)
            )
        )
    }
}

@Composable
fun ResultRow(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = TextSecondaryGray, fontSize = 14.sp)
        Text(text = value, color = valueColor, fontWeight = FontWeight.Black, fontSize = 16.sp)
    }
}