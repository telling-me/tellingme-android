package com.tellingus.tellingme.data.repositoryimpl

import com.tellingus.tellingme.data.model.common.BasicResponse
import com.tellingus.tellingme.data.model.home.PushTokenRequest
import com.tellingus.tellingme.data.model.oauth.UserRequest
import com.tellingus.tellingme.data.model.oauth.UserResponse
import com.tellingus.tellingme.data.model.oauth.login.OauthRequest
import com.tellingus.tellingme.data.model.oauth.login.TokenResponse
import com.tellingus.tellingme.data.model.oauth.signout.SignOutRequest
import com.tellingus.tellingme.data.model.oauth.signup.SignUpRequest
import com.tellingus.tellingme.data.model.oauth.signup.NicknameRequest
import com.tellingus.tellingme.data.model.oauth.signup.NicknameResponse
import com.tellingus.tellingme.data.model.oauth.signup.SignUpResponse
import com.tellingus.tellingme.data.network.NetworkService
import com.tellingus.tellingme.data.network.adapter.ApiResult
import com.tellingus.tellingme.domain.repository.AuthRepository
import retrofit2.http.Body
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val service: NetworkService
): AuthRepository {
    override suspend fun loginFromKakao(
        oauthToken: String,
        loginType: String,
        isAuto: String,
        oauthRequest: OauthRequest
    ): ApiResult<TokenResponse> {
        return service.loginFromKakao(oauthToken, loginType, isAuto, oauthRequest)
    }

    override suspend fun logout(): ApiResult<BasicResponse> {
        return service.logout()
    }

    override suspend fun getUserInfo(): ApiResult<UserResponse> {
        return service.getUserInfo()
    }

    override suspend fun updateUserInfo(
        userRequest: UserRequest
    ): ApiResult<BasicResponse> {
        return service.updateUserInfo(userRequest)
    }

    override suspend fun verifyNickname(nickname: String): ApiResult<BasicResponse> {
        return service.verifyNickname(NicknameRequest(nickname = nickname))
    }

    override suspend fun signUpUser(signupRequest: SignUpRequest): ApiResult<SignUpResponse> {
        return service.signUpUser(signupRequest)
    }

    override suspend fun refreshAccessToken(
        accessToken: String,
        refreshToken: String
    ): ApiResult<TokenResponse> {
        return service.refreshAccessToken(accessToken, refreshToken)
    }

    override suspend fun signOutUser(): ApiResult<BasicResponse> {
        return service.signOutUser(signoutRequest = SignOutRequest())
    }

    override suspend fun updatePushToken(pushToken: String): ApiResult<BasicResponse> {
        return service.updatePushToken(PushTokenRequest(pushToken))
    }

}