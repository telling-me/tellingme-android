package com.tellingus.tellingme.presentation.ui.feature.home.tellercard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tellingus.tellingme.presentation.ui.common.component.widget.ProfileCard
import com.tellingus.tellingme.presentation.ui.common.component.widget.ProfileCardResponse
import com.tellingus.tellingme.presentation.ui.common.const.getColorByColorCode
import com.tellingus.tellingme.presentation.ui.common.navigation.HomeDestinations
import com.tellingus.tellingme.presentation.ui.theme.Primary400
import com.tellingus.tellingme.presentation.ui.theme.Profile200
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme

@Composable
fun MyTellerCard(
    navController: NavController,
    modifier: Modifier = Modifier,
    profileCardResponse: ProfileCardResponse = ProfileCardResponse(
        nickname = "",
        description = "",
        level = "",
        consecutiveWritingDate = "",
        profileIcon = "",
        badgeCode = "",
        colorCode = "",
    )
) {

    Column {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Row {
                Text(text = "나의", style = TellingmeTheme.typography.head2Regular)
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = "텔러 카드", style = TellingmeTheme.typography.head2Bold)
            }
            Text(
                modifier = Modifier.clickable { navController.navigate(HomeDestinations.TELLER_CARD_TUNING) },
                text = "꾸미기", color = Primary400, style = TellingmeTheme.typography.body1Bold
            )
        }
        Box(modifier = Modifier.padding(top = 16.dp)) {
            ProfileCard(
                response = profileCardResponse,
                backgroundColor = getColorByColorCode(profileCardResponse.colorCode)
            )
        }
    }
}

@Preview
@Composable
fun MyTellerCardPreview() {
    MyTellerCard(navController = rememberNavController())
}