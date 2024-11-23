package com.tellingus.tellingme.domain.usecase.notice

import com.tellingus.tellingme.data.model.common.BasicResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.NoticeRepository
import javax.inject.Inject

class NoticeReadByNoticeIdUseCase @Inject constructor(
    private val noticeRepository: NoticeRepository
) {

    suspend operator fun invoke(noticeId: Int): ApiResult<BasicResponse> {
        return noticeRepository.noticeReadByNoticeId(noticeId)
    }
}