package com.tellingus.tellingme.presentation.ui.common.component.badge

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tellingus.tellingme.R


@Composable
fun DiagonalHalfCircleBadge(
    index: Int,
    isChecked: Boolean,
    onCheckChange: (index: Int) -> Unit,
    bottomColor: Color,
    topColor: Color,
    modifier: Modifier = Modifier
) {
    val TAG: String = "로그"
    Log.d(TAG, " - DiagonalHalfCircleBadge() called index: ${index}")
    Box(
        modifier = modifier
            .size(54.dp)
            .background(Color.White)
            .clickable { onCheckChange(index) } // Click event to toggle checked state
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
                .padding(top = 6.dp, end = 8.dp) // Same padding as in BadgeCard
                .zIndex(1f) // Make sure the icon appears above other elements
                .size(16.dp) // Icon size
        )
    }
}