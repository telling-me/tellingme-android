package com.tellingus.tellingme.presentation.ui.common.component.badge


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme


@Composable
fun DiagonalHalfCircleBadge(
    index: Int,
    isChecked: Boolean,
    onCheckChange: (index: Int, colorCode: String) -> Unit,
    bottomColor: Color,
    topColor: Color,
    modifier: Modifier = Modifier,
    colorCode: String = "",
    hasNeedCheese: Boolean = false,
) {
    Box(
        modifier = modifier
            .size(54.dp)
            .background(Color.White)
            .clickable { onCheckChange(index, colorCode) } // Click event to toggle checked state
    ) {
        Canvas(
            modifier = Modifier.matchParentSize()
        ) {
            // Setting up the circle's position and size
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = size.minDimension / 2

            // Drawing the bottomColor semi-circle
            rotate(140f, Offset(centerX, centerY)) {
                drawArc(
                    color = bottomColor,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = true,
                    topLeft = Offset(
                        centerX - radius,
                        centerY - radius
                    ),
                    size = Size(radius * 2, radius * 2)
                )

                // Drawing the topColor semi-circle
                drawArc(
                    color = topColor,
                    startAngle = 0f,
                    sweepAngle = 180f,
                    useCenter = true,
                    topLeft = Offset(
                        centerX - radius,
                        centerY - radius
                    ),
                    size = Size(radius * 2, radius * 2)
                )
            }
        }

        if (hasNeedCheese) {
            Row(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(100.dp))
                    .width(52.dp)
                    .padding(start = 9.dp, end = 9.dp, top = 4.dp, bottom = 4.dp)
                    .align(BottomCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(R.drawable.icon_cheese_14),
                    contentDescription = if (isChecked) "Checked" else "Unchecked",
                    modifier = Modifier
                        .zIndex(1f)
                        .size(14.dp)
                )
                Text(text = "20", style = TellingmeTheme.typography.caption2Bold, color = Gray600)
            }
        }

        // Display the checked or unchecked icon in the top-right corner
        val iconRes = if (isChecked) {
            R.drawable.icon_check_circle_on_white
        } else {
            R.drawable.icon_check_circle_off_white
        }

        Image(
            painter = painterResource(iconRes),
            contentDescription = if (isChecked) "Checked" else "Unchecked",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 0.dp, end = 0.dp) // Same padding as in BadgeCard
                .zIndex(1f)
                .size(24.dp)
        )
    }
}