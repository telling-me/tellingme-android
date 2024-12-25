package com.tellingus.tellingme.data.repositoryimpl

import android.util.Log
import com.tellingus.tellingme.data.model.common.BasicResponse
import com.tellingus.tellingme.data.model.notice.LoadNoticeResponse
import com.tellingus.tellingme.data.model.notice.NoticeSummaryResponse
import com.tellingus.tellingme.data.network.NetworkService
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.NoticeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoticeRepositoryImpl @Inject constructor(
    private val service: NetworkService
) : NoticeRepository {
    override suspend fun loadNotice(): ApiResult<LoadNoticeResponse> {
        return service.loadNotice()
    }

    override suspend fun noticeReadAll(): ApiResult<BasicResponse> {
        return service.noticeReadAll()
    }

    override suspend fun noticeReadByNoticeId(noticeId: Int): ApiResult<BasicResponse> {
        return service.noticeReadByNoticeId(noticeId)
    }

    override suspend fun deleteNoticeByNoticeId(noticeId: Int): ApiResult<BasicResponse> {
        return service.deleteNoticeByNoticeId(noticeId)
    }

    override suspend fun getNoticeSummary(): ApiResult<NoticeSummaryResponse> {
        return service.getNoticeSummary()
    }

}