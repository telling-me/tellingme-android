package com.tellingus.tellingme.presentation.ui.common.const

import androidx.compose.ui.graphics.Color
import com.tellingus.tellingme.presentation.ui.theme.Profile100
import com.tellingus.tellingme.presentation.ui.theme.Profile100_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile200
import com.tellingus.tellingme.presentation.ui.theme.Profile200_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile300
import com.tellingus.tellingme.presentation.ui.theme.Profile300_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile400
import com.tellingus.tellingme.presentation.ui.theme.Profile400_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile500
import com.tellingus.tellingme.presentation.ui.theme.Profile500_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile600
import com.tellingus.tellingme.presentation.ui.theme.Profile600_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile700
import com.tellingus.tellingme.presentation.ui.theme.Profile700_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile800
import com.tellingus.tellingme.presentation.ui.theme.Profile800_Bottom
import com.tellingus.tellingme.presentation.ui.theme.Profile900
import com.tellingus.tellingme.presentation.ui.theme.Profile900_Bottom
import com.tellingus.tellingme.presentation.ui.theme.RED_001

data class ColorCode(
    val colorCode: String,
    val color: Color,
    val topColor: Color,
    val bottomColor: Color,
)

val colorCodeList = listOf<ColorCode>(
    ColorCode(
        "CL_DEFAULT",
        Profile100,
        topColor = Profile100,
        bottomColor = Profile100_Bottom,
    ),
    ColorCode(
        "CL_BLUE_001",
        Profile400,
        topColor = Profile400,
        bottomColor = Profile400_Bottom
    ),
    ColorCode(
        "CL_ORANGE_001",
        Profile700,
        topColor = Profile700,
        bottomColor = Profile700_Bottom
    ),
    ColorCode(
        "CL_RED_001",
        RED_001,
        topColor = Profile900,
        bottomColor = Profile900_Bottom
    ),
    ColorCode(
        "CL_PURPLE_001",
        Profile800,
        topColor = Profile800,
        bottomColor = Profile800_Bottom
    ),
    ColorCode(
        "CL_NAVY_001",
        Profile300,
        topColor = Profile300,
        bottomColor = Profile300_Bottom
    ),
    ColorCode(
        "CL_PINK_001",
        Profile200,
        topColor = Profile200,
        bottomColor = Profile200_Bottom
    ),
    ColorCode(
        "CL_YELLOW_001",
        Profile500,
        topColor = Profile500,
        bottomColor = Profile500_Bottom
    ),
    ColorCode(
        "CL_GREEN_001",
        Profile600,
        topColor = Profile600,
        bottomColor = Profile600_Bottom
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
