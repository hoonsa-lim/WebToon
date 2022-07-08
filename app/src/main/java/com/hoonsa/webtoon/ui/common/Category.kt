package com.hoonsa.webtoon.ui.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.hoonsa.webtoon.ui.extension.Border
import com.hoonsa.webtoon.ui.extension.border
import com.hoonsa.webtoon.ui.theme.Sizes


data class MajorCategoryItemUiState(
    @StringRes val text: Int,
    val isSelected: Boolean = false,
    val showDot: Boolean = false,   //pick 점
    val onClick: () -> Unit,
)

data class MajorCategoryUiState(
    val items: List<MajorCategoryItemUiState>,

    @DrawableRes val iconSearch: Int,
    @StringRes val iconSearchDescription: Int,
    val onClickSearchIcon: () -> Unit,
)

@Composable
fun MajorCategory(uiState: MajorCategoryUiState){
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .border(top = Border(Sizes.borderWidth, Color.LightGray)),

        verticalAlignment = Alignment.CenterVertically,
    ){
        IconButton(onClick = uiState.onClickSearchIcon,){
            Icon(
                modifier = Modifier
                    .padding(Sizes.padding)
                    .size(Sizes.iconSmallSize)
                    .clickable(indication = null, interactionSource = MutableInteractionSource(),){},
                painter = painterResource(id = uiState.iconSearch),
                contentDescription = stringResource(id = uiState.iconSearchDescription)
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .height(Sizes.categoryHeight)
                .horizontalScroll(ScrollState(0), enabled = true)
                .background(MaterialTheme.colorScheme.background),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            uiState.items.forEachIndexed { index, itemState ->
                MajorCategoryItem(index, itemState, totalSize = uiState.items.size)
            }
        }
    }
}


@Composable
fun SubCategory(){

}

@Preview(showBackground = true)
@Composable
fun PreviewBox(){
    Dot(Modifier)
}

@Composable
fun MajorCategoryItem(index: Int, itemState: MajorCategoryItemUiState, totalSize: Int){
    val text = stringResource(id = itemState.text)
    val endPadding =
        if ((totalSize -1) == index) Sizes.padding
        else Sizes.paddingMiddle

    val textModifier = Modifier.padding(start = Sizes.paddingMiddle, end = endPadding)
    val textColor = if (itemState.isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondary

    if (itemState.showDot){
        Box {
            Dot(
                Modifier
                    .align(Alignment.TopEnd)
                    .padding(start = Sizes.paddingMiddle, end = endPadding),
            )
            MajorCategoryItemText(textModifier, textColor, text)
        }
    }else {
        MajorCategoryItemText(textModifier, textColor, text)
    }
}

@Composable
fun MajorCategoryItemText(modifier: Modifier, textColor: Color, text: String) {
    Text(modifier = modifier, text = text, color = textColor)
}

@Composable
fun Dot(modifier: Modifier) {
    Box(
        modifier
            .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
            .clip(CircleShape)
            .size(Sizes.dotSize),
    )
}
