package com.tellingus.tellingme.data.network

import com.tellingus.tellingme.data.model.common.BasicResponse
import com.tellingus.tellingme.data.model.home.HomeRequest
import com.tellingus.tellingme.data.model.home.HomeResponse
import com.tellingus.tellingme.data.model.home.AnswerRequest
import com.tellingus.tellingme.data.model.home.AnswerResponse
import com.tellingus.tellingme.data.model.home.NoticeResponse
import com.tellingus.tellingme.data.model.home.QuestionRequest
import com.tellingus.tellingme.data.model.home.QuestionResponse
import com.tellingus.tellingme.data.model.myspace.AnswerListResponse
import com.tellingus.tellingme.data.model.myspace.MyPageResponse
import com.tellingus.tellingme.data.model.notice.LoadNoticeResponse
import com.tellingus.tellingme.data.model.oauth.login.OauthRequest
import com.tellingus.tellingme.data.model.oauth.login.TokenResponse
import com.tellingus.tellingme.data.model.oauth.signout.SignOutRequest
import com.tellingus.tellingme.data.model.oauth.signup.SignUpRequest
import com.tellingus.tellingme.data.model.oauth.signup.NicknameRequest
import com.tellingus.tellingme.data.model.oauth.signup.NicknameResponse
import com.tellingus.tellingme.data.model.oauth.signup.SignUpResponse
import com.tellingus.tellingme.data.model.otherspace.CommunicationListRequest
import com.tellingus.tellingme.data.model.otherspace.CommunicationListResponse
import com.tellingus.tellingme.data.model.otherspace.CommunicationRequest
import com.tellingus.tellingme.data.model.otherspace.CommunicationResponse
import com.tellingus.tellingme.data.model.user.UserBadgeResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


const val END_POINT: String = "/api"

interface NetworkService {

    // 소셜로그인 API
    @POST("${END_POINT}/oauth/{loginType}/{isAuto}")
    suspend fun loginFromKakao(
        @Header("oauthToken") oauthToken: String,
        @Path("loginType") loginType: String,
        @Path("isAuto") isAuto: String,
        @Body oauthRequest: OauthRequest
    ): ApiResult<TokenResponse>

    // 닉네임 유효성 검사 API
    @POST("${END_POINT}/oauth/nickname")
    suspend fun verifyNickname(
        @Body nicknameRequest: NicknameRequest
    ): ApiResult<NicknameResponse>

    // 추가 정보 기입 API
    @POST("${END_POINT}/oauth/join")
    suspend fun signUpUser(
        @Body signupRequest: SignUpRequest
    ): ApiResult<SignUpResponse>

    @GET("${END_POINT}/notice")
    suspend fun loadNotice(): ApiResult<LoadNoticeResponse>

    // 오늘의 질문 조회 API
    @POST("${END_POINT}/question")
    suspend fun getQuestion(
        @Body questionRequest: QuestionRequest
    ): ApiResult<QuestionResponse>

    // 답변 작성 API
    @POST("${END_POINT}/answer")
    suspend fun writeAnswer(
        @Body answerRequest: AnswerRequest
    ): ApiResult<AnswerResponse>


    // 알림 조회 API
    @GET("${END_POINT}/notice")
    suspend fun getNotice(): ApiResult<NoticeResponse>

    // 토큰 갱신 API
    @POST("${END_POINT}/token")
    suspend fun refreshAccessToken(
        @Header("accessToken") accessToken: String, @Header("refreshToken") refreshToken: String
    ): ApiResult<TokenResponse>

    // 회원 탈퇴 API
    @POST("${END_POINT}/oauth/withdraw/rn")
    suspend fun signOutUser(
        @Body signoutRequest: SignOutRequest
    ): ApiResult<BasicResponse>

    // 내 답변 리스트 조회 API
    @GET("${END_POINT}/answer/list/all")
    suspend fun getAnswerList(): ApiResult<AnswerListResponse>

    // 메인화면용 API
    @POST("${END_POINT}/v2/mobile/main")
    suspend fun getMain(
        @Body homeRequest: HomeRequest
    ): ApiResult<HomeResponse>

    // 알림 전체 읽음
    @POST("${END_POINT}/notice/readAll")
    suspend fun noticeReadAll(): ApiResult<BasicResponse>

    // 특정 알림 읽음
    @POST("${END_POINT}/notice/read/{noticeId}")
    suspend fun noticeReadByNoticeId(
        @Path("noticeId") noticeId: Int,
    ): ApiResult<BasicResponse>

    @DELETE("${END_POINT}/notice/{noticeId}")
    suspend fun deleteNoticeByNoticeId(@Path("noticeId") noticeId: Int): ApiResult<BasicResponse>

    // 모바일 마이 페이지
    @GET("${END_POINT}/v2/mobile/mypage")
    suspend fun getMyPage(): ApiResult<MyPageResponse>

    // 모두의 공간 5일치 질문 리스트
    @GET("${END_POINT}/communication")
    suspend fun getCommunication(
        @Query("date") date: String
    ): ApiResult<CommunicationResponse>

    // 소통 공간 정렬 해서 보기
    @GET("${END_POINT}/communication/list")
    suspend fun getCommunicationList(
        @Query("date") date: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: Int
    ): ApiResult<CommunicationListResponse>

    // 유저 배지 목록
    @GET("${END_POINT}/v2/user/badge")
    suspend fun getUserBadge(): ApiResult<UserBadgeResponse>

}
