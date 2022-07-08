package com.hoonsa.webtoon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hoonsa.webtoon.ui.theme.Sizes
import com.hoonsa.webtoon.ui.theme.WebToonTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainView()
        }
    }
}

@Composable
fun MainView() {
    WebToonTheme {
        BoxWithConstraints() {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            ) {
                val containerHeight = this@BoxWithConstraints.maxHeight - Sizes.bottomNavHeight
                MainScreenContainer(containerHeight)
                BottomNavigationBar(Sizes.bottomNavHeight)
            }
        }
    }
}

@Composable
fun MainScreenContainer(height: Dp) {
    BoxWithConstraints(
        Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .height(height)
    ) {
        Text(text = "asdf")
    }
}

@Composable
fun BottomNavigationBar(height: Dp) {
    val chargingStation = BottomNavItemUiState(R.string.charging_station, R.drawable.icon_coin, R.drawable.icon_coin_on)
    val novel = BottomNavItemUiState(R.string.novel, R.drawable.icon_novel, R.drawable.icon_novel_on)
    val webToon = BottomNavItemUiState(R.string.web_toon, R.drawable.icon_web_toon, R.drawable.icon_web_toon_on, isSelected = true)
    val myLibrary = BottomNavItemUiState(R.string.my_library, R.drawable.icon_library, R.drawable.icon_library_on)
    val menu = BottomNavItemUiState(R.string.login, R.drawable.icon_menu, R.drawable.icon_close, badge = BadgeUiState("15"))

    Row(
        Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .height(height)
            .padding(horizontal = Sizes.paddingDefault),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNaviItem(chargingStation)
        BottomNaviItem(novel)
        BottomNaviItem(webToon)
        BottomNaviItem(myLibrary)
        BottomNaviItem(menu)
    }
}

data class BadgeUiState(
    val text: String,
)

data class BottomNavItemUiState(
    @StringRes val textId: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val iconSelected: Int,
    val isSelected: Boolean = false,
    val badge: BadgeUiState? = null
)

@Composable
fun BottomNaviItem(
    uiState: BottomNavItemUiState,
) {
    val text = stringResource(id = uiState.textId)
    val icon = painterResource(id = if (uiState.isSelected) uiState.iconSelected else uiState.icon)
    val iconSize = if (uiState.badge == null) Sizes.iconSize else Sizes.iconSize - 4.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(Modifier.size(Sizes.iconSize)) {
            Icon(
                modifier = Modifier
                    .size(iconSize)
                    .align(Alignment.BottomStart),
                painter = icon,
                contentDescription = text,
            )
            TextBadge(Modifier.align(Alignment.TopEnd), uiState.badge,)
        }

        Text(text = text, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun TextBadge(modifier: Modifier, badge: BadgeUiState?) {
    badge?.let {
        Box(
            modifier
                .border(BorderStroke(width = 1.dp, Color.Black), shape = CircleShape,)
                .background(MaterialTheme.colorScheme.background, shape = CircleShape)
                .clip(CircleShape)
                .size(Sizes.badgeSize),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = it.text,
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainView()
}