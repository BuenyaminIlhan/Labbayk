package de.bueny.labbayk.util

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

fun customTextStyle() = TextStyle(
    fontSize = 24.sp,
    lineHeight = 30.sp,
    fontWeight = FontWeight.Bold,
    fontFamily = FontFamily.Serif,
    textAlign = TextAlign.Right
)