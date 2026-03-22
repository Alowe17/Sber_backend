package com.example.myapplication.presentation.view.ratingScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.*

// Модель данных для участника рейтинга
data class RatingUser(
    val name: String,
    val points: Int,
    val isCurrentUser: Boolean = false
)

@Composable
fun RatingScreen() {
    // Состояние переключателя (0 - Дилер, 1 - Регион)
    var selectedTab by remember { mutableIntStateOf(0) }

    // Тестовые данные
    val dealerRating = listOf(
        RatingUser("Алексей Петров", 1540),
        RatingUser("Мария Сидорова", 1420),
        RatingUser("Иван Иванов (Вы)", 1310, true),
        RatingUser("Елена Кузнецова", 1280),
        RatingUser("Дмитрий Волков", 1150),
        RatingUser("Ольга Новикова", 1090),
        RatingUser("Сергей Попов", 980),
        RatingUser("Анна Соколова", 850),
        RatingUser("Павел Морозов", 720),
        RatingUser("Вера Васильева", 610)
    )

    val regionRating = listOf(
        RatingUser("Константин К.", 2100),
        RatingUser("Ирина С.", 1950),
        RatingUser("Михаил Ю.", 1820),
        RatingUser("Иван Иванов (Вы)", 1310, true) // В регионе позиция может быть другой
    )

    val currentList = if (selectedTab == 0) dealerRating else regionRating

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
        // Заголовок
        Column(modifier = Modifier.padding(top = 48.dp, start = 20.dp, end = 20.dp, bottom = 16.dp)) {
            Text(
                text = "Рейтинг",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = SberBlack
            )
        }

        // Переключатель (TabSelector)
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent,
            contentColor = SberGreen,
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = SberGreen
                )
            },
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Внутри дилера", fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.Groups, contentDescription = null) }
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Топ региона", fontWeight = FontWeight.Bold) },
                icon = { Icon(Icons.Default.LocationOn, contentDescription = null) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Список участников
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(currentList) { index, user ->
                RatingItemCard(rank = index + 1, user = user)
            }
        }

        // Нижняя панель с моим местом (Sticky Footer)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = SberBlack,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(24.dp)
                    .navigationBarsPadding(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Ваше место", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                    Text(
                        text = if (selectedTab == 0) "3 из 45" else "114 из 1200",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                }
                Icon(
                    imageVector = Icons.Default.EmojiEvents,
                    contentDescription = null,
                    tint = Color(0xFFFFD700), // Золотой цвет
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
fun RatingItemCard(rank: Int, user: RatingUser) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (user.isCurrentUser) SberGreen.copy(alpha = 0.15f) else Color.White.copy(alpha = 0.7f)
        ),
        shape = RoundedCornerShape(16.dp),
        border = if (user.isCurrentUser) BorderStroke(1.dp, SberGreen) else null
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Номер места
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(if (rank <= 3) SberGreen else DividerGray.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = rank.toString(),
                    color = if (rank <= 3) Color.White else SberBlack,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Имя
            Text(
                text = user.name,
                modifier = Modifier.weight(1f),
                fontWeight = if (user.isCurrentUser) FontWeight.Black else FontWeight.Medium,
                color = SberBlack,
                fontSize = 15.sp
            )

            // Баллы
            Text(
                text = "${user.points} б.",
                fontWeight = FontWeight.ExtraBold,
                color = SberGreen,
                fontSize = 16.sp
            )
        }
    }
}