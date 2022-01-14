package com.example.e_progression.presentation.main.components

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_progression.common.Constants
import com.example.e_progression.presentation.Screen
import com.example.e_progression.presentation.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun BottomMenu(
    items:List<BottomNavigationItem>,
    modifier: Modifier = Modifier,
    activeHighlightColor: Color = gradientColor4,
    activeTextColor: Color = Color.Black,
    inactiveTextColor: Color = AquaBlue,
    initialSelectedItemIndex: Int = 0,
    navController: NavController
){
    //Log.d("BottomMenu: ",items.toString())
    var selectedItemIndex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(15.dp)
    ) {
        items.forEachIndexed { index, item ->
            BottomMenuItem(
                item = item,
                isSelected = index == selectedItemIndex,
                activeHighlightColor = activeHighlightColor,
                activeTextColor = activeTextColor,
                inactiveTextColor = inactiveTextColor,
                onItemClick = {
                    navController.navigate(item.route+"/${Constants.PARAM_USER_UUID}")
                    selectedItemIndex = index
                }
            )
        }
    }
}


