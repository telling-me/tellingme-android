package com.tellingus.tellingme.presentation.ui.common.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tellingus.tellingme.presentation.ui.theme.Background100
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme

@Composable
fun EmotionChip(modifier: Modifier = Modifier, feeling: String = "happy", emotion: Int = 0) {
    Column(
        modifier = modifier
            .background(Background100, shape = RoundedCornerShape(4.dp))
            .height(24.dp)
            .padding(start = 6.dp, end = 6.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = getEmotion(emotion), style = TellingmeTheme.typography.body2Bold, color = Gray600
        )
    }
}


fun getEmotion(data: Int): String {
    return when (data) {
        0 -> "행복해요"
        1 -> "뿌듯해요"
        2 -> "그저 그래요"
        3 -> "피곤해요"
        4 -> "슬퍼요"
        5 -> "화나요"
        6 -> "설레요"
        7 -> "신나요"
        8 -> "편안해요"
        9 -> "무기력해요"
        10 -> "외로워요"
        11 -> "복잡해요"
        else -> ""
    }
}