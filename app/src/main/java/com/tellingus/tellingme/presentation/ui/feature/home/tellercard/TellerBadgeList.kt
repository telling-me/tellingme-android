package com.tellingus.tellingme.presentation.ui.feature.home.tellercard

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tellingus.tellingme.R
import com.tellingus.tellingme.presentation.ui.common.component.badge.TellerBadge
import com.tellingus.tellingme.presentation.ui.common.component.chip.ActionChip
import com.tellingus.tellingme.presentation.ui.theme.Base0
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import com.tellingus.tellingme.presentation.ui.theme.Typography


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TellerBadgeList(modifier: Modifier = Modifier) {
    val cardList = listOf("1", "2", "3", "4")
    val pagerState = rememberPagerState(pageCount = { cardList.size })


    Column(modifier = Modifier.padding(start = 20.dp)) {
        Text(text = "내가 받은 텔러 배지", style = TellingmeTheme.typography.body1Bold)
        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(149.dp),
            modifier = Modifier.padding(top = 12.dp),
            pageSpacing = 12.dp,
            contentPadding = PaddingValues(end = 20.dp),
        ) { page ->
            TellerBadge(title = "또 오셨네요", content = "단골 텔러")
        }
    }


    // empty 일때 노출
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Image(
//            painter = painterResource(id = R.drawable.empty_character),
//            contentDescription = "empty_character"
//        )
//        Text(text = "아직 텔러들의 이야기가 없어요", style = Typography.body2Bold, color = Gray600)
//        Text(
//            text = "첫 글을 작성하면 받을지도 몰라요!",
//            style = Typography.body2Regular,
//            color = Gray600
//        )
//
//        Column(
//            modifier = Modifier
//                .padding(top = 14.dp)
//                .fillMaxWidth(),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            ActionChip(
//                text = "글 작성하기",
//                onClick = {}
//            )
//        }
//    }

}
