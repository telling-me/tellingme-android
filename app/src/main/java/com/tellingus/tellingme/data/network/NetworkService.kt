package com.tellingus.tellingme.data.network

import com.tellingus.tellingme.data.model.common.BasicResponse
import com.tellingus.tellingme.data.model.home.HomeRequest
import com.tellingus.tellingme.data.model.home.HomeResponse
import com.tellingus.tellingme.data.model.home.AnswerRequest
import com.tellingus.tellingme.data.model.home.AnswerResponse
import com.tellingus.tellingme.data.model.home.DeleteAnswerRequest
import com.tellingus.tellingme.data.model.home.MobileTellerCardResponse
import com.tellingus.tellingme.data.model.home.NoticeResponse
import com.tellingus.tellingme.data.model.home.NoticeRewardResponse
import com.tellingus.tellingme.data.model.home.PatchTellerCardRequest
import com.tellingus.tellingme.data.model.home.PatchTellerCardResponse
import com.tellingus.tellingme.data.model.home.PushTokenRequest
import com.tellingus.tellingme.data.model.home.QuestionRequest
import com.tellingus.tellingme.data.model.home.QuestionResponse
import com.tellingus.tellingme.data.model.home.UpdateAnswerRequest
import com.tellingus.tellingme.data.model.myspace.AnswerListResponse
import com.tellingus.tellingme.data.model.myspace.MyPageResponse
import com.tellingus.tellingme.data.model.notice.LoadNoticeResponse
import com.tellingus.tellingme.data.model.oauth.UserRequest
import com.tellingus.tellingme.data.model.oauth.UserResponse
import com.tellingus.tellingme.data.model.oauth.login.OauthRequest
import com.tellingus.tellingme.data.model.oauth.login.TokenResponse
import com.tellingus.tellingme.data.model.oauth.signout.SignOutRequest
import com.tellingus.tellingme.data.model.oauth.signup.SignUpRequest
import com.tellingus.tellingme.data.model.oauth.signup.NicknameRequest
import com.tellingus.tellingme.data.model.oauth.signup.NicknameResponse
import com.tellingus.tellingme.data.model.oauth.signup.SignUpResponse
import com.tellingus.tellingme.data.model.otherspace.CommunicationListResponse
import com.tellingus.tellingme.data.model.otherspace.CommunicationResponse
import com.tellingus.tellingme.data.model.otherspace.GetAnswerByIdResponse
import com.tellingus.tellingme.data.model.otherspace.PostLikesRequest
import com.tellingus.tellingme.data.model.otherspace.PostLikesResponse
import com.tellingus.tellingme.data.model.otherspace.PostReportRequest
import com.tellingus.tellingme.data.model.user.GetCheeseResponse
import com.tellingus.tellingme.data.model.user.GetNotificationResponse
import com.tellingus.tellingme.data.model.user.PurchaseRequest
import com.tellingus.tellingme.data.model.user.PurchaseResponse
import com.tellingus.tellingme.data.model.user.UpdateNotificationRequest
import com.tellingus.tellingme.data.model.user.UpdateNotificationResponse
import com.tellingus.tellingme.data.model.user.UsableEmotionResponse
import com.tellingus.tellingme.data.model.user.UserBadgeResponse
import com.tellingus.tellingme.data.model.user.UserColorResponse
import com.tellingus.tellingme.data.network.adapter.ApiResult
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.PATCH
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
    ): ApiResult<BasicResponse>

    // 추가 정보 기입 API
    @POST("${END_POINT}/oauth/join")
    suspend fun signUpUser(
        @Body signupRequest: SignUpRequest
    ): ApiResult<SignUpResponse>

    @GET("${END_POINT}/notice")
    suspend fun loadNotice(): ApiResult<LoadNoticeResponse>

    // 오늘의 질문 조회 API
    @GET("${END_POINT}/question")
    suspend fun getQuestion(
        @Query("date") date: String
    ): ApiResult<QuestionResponse>

    // 답변 작성 API
    @POST("${END_POINT}/answer")
    suspend fun writeAnswer(
        @Body answerRequest: AnswerRequest
    ): ApiResult<BasicResponse>

    // 답변 수정 API
    @PATCH("${END_POINT}/answer/update")
    suspend fun updateAnswer(
        @Body updateAnswerRequest: UpdateAnswerRequest
    ): ApiResult<BasicResponse>

    // 답변 삭제 API
    @HTTP(method = "DELETE", path = "${END_POINT}/answer/delete", hasBody = true)
    suspend fun deleteAnswer(
        @Body deleteAnswerRequest: DeleteAnswerRequest
    ): ApiResult<BasicResponse>

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

    // 유저정보 조회 API
    @GET("${END_POINT}/user")
    suspend fun getUserInfo(): ApiResult<UserResponse>

    // 유저정보 업데이트 API
    @PATCH("${END_POINT}/user/update")
    suspend fun updateUserInfo(
        @Body userRequest: UserRequest
    ): ApiResult<BasicResponse>

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
        @Query("sort") sort: String
    ): ApiResult<CommunicationListResponse>

    // 유저 배지 목록
    @GET("${END_POINT}/v2/user/badge")
    suspend fun getUserBadge(): ApiResult<UserBadgeResponse>

    @GET("${END_POINT}/v2/user/color")
    suspend fun getUserColor(): ApiResult<UserColorResponse>

    // 좋아요 누르기/취소하기
    @POST("${END_POINT}/likes")
    suspend fun postLikes(
        @Body postLikesRequest: PostLikesRequest
    ): ApiResult<PostLikesResponse>

    // 고유 id로 답변 조회 API
    @GET("${END_POINT}/answer/id")
    suspend fun getAnswerById(
        @Query("answerId") answerId: Int,
    ): ApiResult<GetAnswerByIdResponse>

    // 날짜로 답변 조회 API
    @GET("${END_POINT}/answer/date")
    suspend fun getAnswerByDate(
        @Query("date") date: String,
    ): ApiResult<AnswerResponse>

    // 신고 등록, reason : 1~6
    @POST("${END_POINT}/report")
    suspend fun postReport(
        @Body postReportRequest: PostReportRequest
    ): ApiResult<Unit>

    // 로그아웃
    @POST("${END_POINT}/oauth/logout")
    suspend fun logout(): ApiResult<BasicResponse>

    // 치즈 개수 조회
    @GET("${END_POINT}/v2/cheese")
    suspend fun getCheese(): ApiResult<GetCheeseResponse>

    // 모바일 텔러카드
    @GET("${END_POINT}/v2/mobile/tellercard")
    suspend fun getMobileTellerCard(): ApiResult<MobileTellerCardResponse>

    // 텔러카드 colorCode / badgeCode 수정
    @PATCH("${END_POINT}/v2/tellercard")
    suspend fun patchTellerCard(@Body patchTellerCardRequest: PatchTellerCardRequest): ApiResult<PatchTellerCardResponse>

    // 알림 상태 반환
    @GET("${END_POINT}/user/notification")
    suspend fun getNotification(): ApiResult<GetNotificationResponse>

    // 알림 상태 변경
    @POST("${END_POINT}/update/notification")
    suspend fun updateNotification(@Body updateNotificationRequest: UpdateNotificationRequest): ApiResult<UpdateNotificationResponse>

    // 푸시토큰 업데이트
    @POST("${END_POINT}/v2/payment")
    suspend fun purchaseEmotion(
        @Body purchaseRequest: PurchaseRequest
    ): ApiResult<PurchaseResponse>

    // 푸시토큰 업데이트
    @POST("${END_POINT}/user/update/pushToken")
    suspend fun updatePushToken(
        @Body pushTokenRequest: PushTokenRequest
    ): ApiResult<BasicResponse>

    // 사용가능한 감정 반환
    @GET("${END_POINT}/v2/user/emotion")
    suspend fun getUsableEmotion(): ApiResult<UsableEmotionResponse>

    // 보상 알림 조회 API
    @GET("${END_POINT}/notice/reward")
    suspend fun getNoticeReward(): ApiResult<NoticeRewardResponse>
}
