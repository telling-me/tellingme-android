package com.tellingus.tellingme.presentation.ui.feature.home.tellercard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tellingus.tellingme.R
import com.tellingus.tellingme.data.model.home.Badge
import com.tellingus.tellingme.presentation.ui.common.component.badge.TellerBadge
import com.tellingus.tellingme.presentation.ui.common.component.chip.ActionChip
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TellerBadgeList(modifier: Modifier = Modifier, badges: List<Badge> = emptyList()) {
    val pagerState = rememberPagerState(pageCount = { badges.size })

    if (badges.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp, top = 20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty_character),
                contentDescription = "empty_character"
            )
            Text(
                text = "아직 내가 받은 뱃지가 없어요",
                style = TellingmeTheme.typography.body2Bold,
                color = Gray600
            )
            Text(
                text = "첫 글을 작성하면 받을지도 몰라요!",
                style = TellingmeTheme.typography.body2Regular,
                color = Gray600
            )

//            Column(
//                modifier = Modifier
//                    .padding(top = 14.dp)
//                    .fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                ActionChip(
//                    text = "글 작성하기",
//                    onClick = {}
//                )
//            }
        }

    } else {
        Column(modifier = Modifier.padding(start = 20.dp)) {
            Text(text = "내가 받은 텔러 배지", style = TellingmeTheme.typography.body1Bold)
            HorizontalPager(
                state = pagerState,
                pageSize = PageSize.Fixed(149.dp),
                modifier = Modifier.padding(top = 12.dp),
                pageSpacing = 12.dp,
                contentPadding = PaddingValues(end = 20.dp),
            ) { page ->
                val item = badges[page]
                TellerBadge(
                    title = "${item.badgeName}",
                    content = "${item.badgeMiddleName}",
                    badgeCode = item.badgeCode
                )
            }
        }
    }


}
