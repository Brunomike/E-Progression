package com.example.e_progression.presentation.auth.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.e_progression.presentation.ui.theme.gradientColor5

@Composable
fun Dropdown(
    text: String,
    modifier: Modifier = Modifier,
    initiallyOpened: Boolean,
    content: @Composable (modifier:Modifier) -> Unit
    ) {
        var isOpen by remember {
            mutableStateOf(initiallyOpened)
        }
        val alpha = animateFloatAsState(
            targetValue = if (isOpen) 1f else 0f,
            animationSpec = tween(
                durationMillis = 300
            )
        )
        val rotateX = animateFloatAsState(
            targetValue = if (isOpen) 0f else -90f,
            animationSpec = tween(
                durationMillis = 300
            )
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
            ,modifier = modifier
                .fillMaxWidth()

                //.background(if (textColor == Color.Black) Color.White else Color.White)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .background(Color.White)
                    //.clip(RoundedCornerShape(5.dp))
            ) {
                Text(text = text, modifier = Modifier.padding(start=16.dp),color = Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Open or close the drop down",
                    tint = Color.DarkGray,
                    modifier = Modifier
                        .clickable {
                            isOpen = !isOpen
                        }
                        .scale(1f, if (isOpen) -1f else 1f)
                        .size(40.dp)

                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .graphicsLayer {
                        transformOrigin = TransformOrigin(0.5f, 0f)
                        rotationX = rotateX.value
                    }
                    .alpha(alpha.value)
                    .background(gradientColor5)
            ) {
                if (isOpen){
                    content(modifier=Modifier)
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }


    }