package com.tellingus.tellingme.presentation.ui.feature.otherspace.list

import androidx.lifecycle.viewModelScope
import com.tellingus.tellingme.data.model.otherspace.CommunicationListData
import com.tellingus.tellingme.data.model.otherspace.Pageable
import com.tellingus.tellingme.data.model.otherspace.Sort
import com.tellingus.tellingme.data.network.adapter.onFailure
import com.tellingus.tellingme.data.network.adapter.onSuccess
import com.tellingus.tellingme.domain.usecase.otherspace.GetCommunicationListUseCase
import com.tellingus.tellingme.presentation.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class OtherSpaceListViewModel @Inject constructor(
    private val getCommunicationListUseCase: GetCommunicationListUseCase
) : BaseViewModel<OtherSpaceListContract.State, OtherSpaceListContract.Event, OtherSpaceListContract.Effect>(
    initialState = OtherSpaceListContract.State(
        communicationListData = CommunicationListData(
            content = emptyList(),
            pageable = Pageable(
                sort = Sort(empty = true, sorted = false, unsorted = true),
                offset = 0,
                pageNumber = 0,
                pageSize = 0,
                paged = false,
                unpaged = true
            ),
            totalPages = 0,
            totalElements = 0,
            last = true,
            size = 0,
            number = 0,
            sort = Sort(empty = true, sorted = false, unsorted = true),
            numberOfElements = 0,
            first = true,
            empty = true
        )
    )
) {

    init {
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        getCommunicationList(date, page = 0, size = 1, sort = 1)
    }

    fun getCommunicationList(date: String, page: Int, size: Int, sort: Int) {
        viewModelScope.launch {
            getCommunicationListUseCase(date, page, size, sort).onSuccess {
                updateState(currentState.copy(communicationListData = it.data))
            }.onFailure { s, i -> }
        }
    }

    override fun reduceState(event: OtherSpaceListContract.Event) {
        when (event) {
            is OtherSpaceListContract.Event.OnClickRecently -> {

            }

            is OtherSpaceListContract.Event.OnClickRelative -> {

            }

            is OtherSpaceListContract.Event.OnClickEmpathy -> {

            }

            is OtherSpaceListContract.Event.OnClickHeart -> {

            }
        }
    }
}