package com.tellingus.tellingme.presentation.ui.feature.myspace

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tellingus.tellingme.presentation.ui.theme.Gray100
import com.tellingus.tellingme.presentation.ui.theme.Gray600
import com.tellingus.tellingme.presentation.ui.theme.TellingmeTheme
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.time.LocalDate

@Composable
fun CustomDatePicker(
    startYear: Int,
    startMonth: Int,
    selectedDate: (String, String) -> Unit
) {
    val year = remember { (2020..LocalDate.now().year).map { it.toString() } }
    val month = remember { (1..12).map { it.toString() } }
    val yearPickerState = rememberPickerState()
    val monthPickerState = rememberPickerState()
    var dateHeight by remember { mutableStateOf(0) }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(pixelsToDp(dateHeight))
                        .background(
                            shape = RoundedCornerShape(8.dp),
                            color = Gray100
                        )
                )

                Row {
                    Picker(
                        state = yearPickerState,
                        items = year,
                        startIndex = year.indexOf(startYear.toString()),
                        visibleItemsCount = 3,
                        onChangedDateHeight = { dateHeight = it },
                        isYear = true
                    )
                    Spacer(modifier = Modifier.size(28.dp))
                    Picker(
                        state = monthPickerState,
                        items = month,
                        startIndex = month.indexOf(startMonth.toString()),
                        visibleItemsCount = 3,
                        onChangedDateHeight = { dateHeight = it },
                        isYear = false
                    )
                }
            }

            selectedDate(
                yearPickerState.selectedItem,
                monthPickerState.selectedItem
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Picker(
    modifier: Modifier = Modifier,
    items: List<String>,
    state: PickerState = rememberPickerState(),
    startIndex: Int = 0,
    visibleItemsCount: Int = 3,
    onChangedDateHeight: (Int) -> Unit = {},
    isYear: Boolean
) {
    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = Integer.MAX_VALUE
    val listScrollMiddle = listScrollCount / 2
    val listStartIndex = listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + startIndex

    fun getItem(index: Int) = items[index % items.size]

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val itemHeightPixels = remember { mutableStateOf(0) }
    val itemHeightDp = pixelsToDp(itemHeightPixels.value)

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> getItem(index + visibleItemsMiddle) }
            .distinctUntilChanged()
            .collect { item ->
                state.selectedItem = item
            }
    }

    Box {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .height(itemHeightDp * visibleItemsCount)
                .fadingEdge(fadingEdgeGradient)
        ) {
            items(listScrollCount) { index ->
                Text(
                    modifier = modifier
                        .onSizeChanged { size ->
                            itemHeightPixels.value = size.height
                            onChangedDateHeight(size.height)
                        }
                        .then(Modifier.padding(8.dp)),
                    text = if (isYear) getItem(index) + "년" else getItem(index) + "월",
                    style = TellingmeTheme.typography.head2Regular.copy(
                        color = Gray600,
                        fontSize = 20.sp
                    )
                )
            }
        }
    }
}

@Composable
fun rememberPickerState() = remember { PickerState() }

class PickerState {
    var selectedItem by mutableStateOf("")
}

private fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

@Composable
private fun pixelsToDp(pixels: Int) = with(LocalDensity.current) { pixels.toDp() }