package com.tellingus.tellingme.presentation.ui.common.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tellingus.tellingme.presentation.ui.theme.Base0
import com.tellingus.tellingme.presentation.ui.theme.Gray300

@Composable
fun CustomBottomSheet(containerModifier: Modifier = Modifier, contentModifier : Modifier = Modifier, content: @Composable () -> Unit) {
    Column(
        modifier = containerModifier.background(
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp), color = Base0
        ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.padding(vertical = 16.dp)) {
            Box(
                modifier = Modifier
                    .background(
                        color = Gray300,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .size(width = 32.dp, height = 4.dp)
            )
        }

        Column(
            modifier = contentModifier
                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 8.dp)
        ) {
            content()
        }
    }
}