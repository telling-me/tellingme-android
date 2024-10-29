package com.tellingus.tellingme.presentation.ui.common.component.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tellingus.tellingme.presentation.ui.common.model.ToolTipType
import com.tellingus.tellingme.presentation.ui.theme.Base0
import com.tellingus.tellingme.presentation.ui.theme.Gray500
import com.tellingus.tellingme.presentation.ui.theme.Profile100
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme

@Composable
fun ToolTip(
    modifier: Modifier = Modifier,
    type: ToolTipType,
    text: String,
    hasTriangle: Boolean = false,
) {

    if (hasTriangle) {
        Box() {
            // 왼쪽 상단 삼각형
            Canvas(
                modifier = Modifier
                    .size(16.dp)
                    .align(Alignment.TopStart)
                    .offset(16.dp),
            ) {
                val trianglePath = Path().apply {
                    // 왼쪽 아래 모서리에서 시작
                    moveTo(0f, size.height)

                    // 위쪽 꼭짓점에 곡선 추가
                    quadraticBezierTo(
                        size.width / 2, -3.dp.toPx(), // 꼭짓점 바로 위에 있는 제어 지점 (곡선의 정도를 조절)
                        size.width, size.height // 오른쪽 아래 모서리
                    )

                    close() // 경로를 닫아서 삼각형 형태 완성
                }


                drawPath(
                    path = trianglePath,
                    color = when (type) {
                        ToolTipType.BASIC -> Profile100
                        ToolTipType.HELP -> Gray500
                    }
                )
            }
        }
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                when (type) {
                    ToolTipType.BASIC -> Profile100
                    ToolTipType.HELP -> Gray500
                }
            )
            .padding(vertical = 8.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TellingmeTheme.typography.caption1Bold,
            color = Base0
        )
    }


}

@Preview
@Composable
fun ToolTipPreview() {
    Column {
        ToolTip(
            type = ToolTipType.BASIC,
            text = "감정을 선택해주세요!"
        )
        ToolTip(
            type = ToolTipType.HELP,
            text = "감정을 선택해주세요!"
        )
    }
}