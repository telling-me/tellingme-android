package com.tellingus.tellingme.presentation.ui.common.const

import androidx.compose.ui.graphics.Color
import com.tellingus.tellingme.presentation.ui.theme.Base100
import com.tellingus.tellingme.presentation.ui.theme.Profile100
import com.tellingus.tellingme.presentation.ui.theme.Profile200
import com.tellingus.tellingme.presentation.ui.theme.Profile300
import com.tellingus.tellingme.presentation.ui.theme.Profile400
import com.tellingus.tellingme.presentation.ui.theme.Profile500
import com.tellingus.tellingme.presentation.ui.theme.Profile600
import com.tellingus.tellingme.presentation.ui.theme.Profile700
import com.tellingus.tellingme.presentation.ui.theme.Profile800
import com.tellingus.tellingme.presentation.ui.theme.RED_001

data class ColorCode(
    val colorCode: String,
    val color: Color
)

val colorCodeList = listOf<ColorCode>(
    ColorCode(
        "CL_DEFAULT",
        Profile100
    ),
    ColorCode(
        "CL_BLUE_001",
        Profile400
    ),
    ColorCode(
        "CL_ORANGE_001",
        Profile700
    ),
    ColorCode(
        "CL_RED_001",
        RED_001
    ),
    ColorCode(
        "CL_PURPLE_001",
        Profile800
    ),
    ColorCode(
        "CL_NAVY_001",
        Profile300
    ),
    ColorCode(
        "CL_PINK_001", Profile200
    ),
    ColorCode(
        "CL_YELLOW_001",
        Profile500
    ),
    ColorCode(
        "CL_GREEN_001",
        Profile600
    )
)

fun getColorByColorCode(colorCode: String): Color {
    colorCodeList.forEach {
        if (it.colorCode == colorCode) {
            return it.color
        }
    }
    return Profile100
}
