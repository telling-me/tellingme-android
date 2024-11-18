package com.tellingus.tellingme.presentation.ui.common.component.card

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.theme.Gray300
import com.tellingus.tellingme.presentation.ui.theme.Gray50
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

@Composable
fun CalendarCardView(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    emotion: Int,
    emotionText: String,
    date: LocalDate,
    contents: String
) {
    Column(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            )
            .padding(start = 20.dp, end = 20.dp, top = 32.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(6.dp),
                    color = Gray50
                )
                .padding(horizontal = 6.dp, vertical = 1.5.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Q",
                style = TellingmeTheme.typography.body1Regular.copy(
                    color = Gray300,
                    fontSize = 14.sp
                )
            )
        }

        Text(
            modifier = Modifier
                .padding(top = 10.dp),
            text = title,
            style = TellingmeTheme.typography.body1Regular.copy(
                color = Gray300,
                fontSize = 14.sp
            ),
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .padding(top = 7.dp),
            text = subTitle,
            style = TellingmeTheme.typography.body1Regular.copy(
                color = Gray600,
                fontSize = 12.sp
            ),
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = ImageVector.vectorResource(emotion),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(4.dp))
            Box(
                modifier = Modifier
                    .background(
                        shape = RoundedCornerShape(4.dp),
                        color = Gray50
                    )
                    .padding(horizontal = 6.dp, vertical = 1.5.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emotionText,
                    style = TellingmeTheme.typography.body1Regular.copy(
                        color = Gray300,
                        fontSize = 14.sp
                    )
                )
            }
        }
        Spacer(modifier = Modifier.size(20.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = date.toString().replace("-", "."),
            style = TellingmeTheme.typography.body1Regular.copy(
                color = Gray600,
                fontSize = 12.sp
            ),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.size(8.dp))

        Text(
            modifier = Modifier.fillMaxSize(),
            text = contents,
            style = TellingmeTheme.typography.body1Regular.copy(
                color = Gray600,
                fontSize = 14.sp
            ),
            textAlign = TextAlign.Start
        )
    }
}
