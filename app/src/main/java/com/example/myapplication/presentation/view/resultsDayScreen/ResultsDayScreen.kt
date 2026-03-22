package com.example.myapplication.presentation.view.resultsDayScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.navigation.Screen
import com.example.myapplication.ui.theme.*

@Composable
fun ResultsDayScreen(
    navHostController: NavHostController
) {
    // Состояния для ввода данных
    var dealsCount by remember { mutableStateOf("") }
    var creditVolume by remember { mutableStateOf("") }
    var productsCount by remember { mutableStateOf("") }

    // Фон с фирменным градиентом
    val backgroundGradient = Brush.linearGradient(
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
            .background(backgroundGradient)
    ) {
        // Шапка экрана (Header)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 48.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Результаты дня",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = SberBlack
            )
            Image(
                painter = painterResource(id = R.drawable.sber_logo),
                contentDescription = "Sber Logo",
                modifier = Modifier.height(24.dp),
                contentScale = ContentScale.Fit
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "Введите ваши показатели за сегодня, чтобы система рассчитала ваш прогресс",
                fontSize = 15.sp,
                color = SberBlack.copy(alpha = 0.7f),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Карточка с полями ввода
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    // 1. Оформлено сделок
                    DayResultInputRow(
                        label = "Оформлено сделок",
                        unit = "шт.",
                        value = dealsCount,
                        onValueChange = { dealsCount = it }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = DividerGray.copy(alpha = 0.3f)
                    )

                    // 2. Объем кредитов
                    DayResultInputRow(
                        label = "Объем кредитов",
                        unit = "млн руб.",
                        value = creditVolume,
                        onValueChange = { creditVolume = it }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = DividerGray.copy(alpha = 0.3f)
                    )

                    // 3. Доп. продукты
                    DayResultInputRow(
                        label = "Доп. продукты",
                        unit = "шт.",
                        value = productsCount,
                        onValueChange = { productsCount = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Информационный блок от СберКота
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = BackgroundLightGray.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Row(
                    modifier = Modifier.padding(0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cat_like),
                        contentDescription = "СберКот",
                        modifier = Modifier.size(140.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "СОВЕТ ОТ СБЕРКОТА",
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = SberGreen,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Данные обновятся в системе в течение 15 минут после сохранения.",
                            fontSize = 14.sp,
                            color = SberBlack,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // Нижняя кнопка перехода
        Box(modifier = Modifier.padding(20.dp).padding(bottom = 10.dp)) {
            Button(
                onClick = { navHostController.navigate(Screen.Details.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SberGreen),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = "Детализация рейтинга",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }
    }
}

/**
 * Вспомогательный компонент для строки ввода данных (Label + TextField)
 */
@Composable
private fun DayResultInputRow(
    label: String,
    unit: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = SberBlack
            )
            Text(
                text = unit,
                fontSize = 12.sp,
                color = TextSecondaryGray
            )
        }

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.width(110.dp),
            placeholder = { Text("0", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = SberGreen,
                unfocusedIndicatorColor = SberGreen.copy(alpha = 0.3f),
                cursorColor = SberGreen
            ),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Black,
                fontSize = 20.sp,
                color = SberGreen
            )
        )
    }
}