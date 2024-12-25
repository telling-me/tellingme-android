package com.tellingus.tellingme.domain.repository

import com.tellingus.tellingme.data.model.common.BasicResponse
import com.tellingus.tellingme.data.model.notice.LoadNoticeResponse
import com.tellingus.tellingme.data.model.notice.NoticeSummaryResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult

interface NoticeRepository {
    suspend fun loadNotice(): ApiResult<LoadNoticeResponse>
    suspend fun noticeReadAll(): ApiResult<BasicResponse>
    suspend fun noticeReadByNoticeId(noticeId: Int): ApiResult<BasicResponse>
    suspend fun deleteNoticeByNoticeId(noticeId: Int): ApiResult<BasicResponse>
    suspend fun getNoticeSummary() : ApiResult<NoticeSummaryResponse>
}