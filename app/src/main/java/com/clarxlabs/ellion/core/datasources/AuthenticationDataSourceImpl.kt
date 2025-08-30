package com.clarxlabs.ellion.core.datasources

import com.clarxlabs.ellion.core.AppAPI
import com.clarxlabs.ellion.core.datasources.factories.HttpRequestFactory
import com.clarxlabs.ellion.core.dtos.APIResponse
import com.clarxlabs.ellion.core.repositories.types.ConfirmError
import com.clarxlabs.ellion.core.repositories.types.ConfirmInput
import com.clarxlabs.ellion.core.repositories.types.ConfirmOutput
import com.clarxlabs.ellion.core.repositories.types.ListUsersError
import com.clarxlabs.ellion.core.repositories.types.ListUsersOutput
import com.clarxlabs.ellion.core.repositories.types.SignInError
import com.clarxlabs.ellion.core.repositories.types.SignInInput
import com.clarxlabs.ellion.core.repositories.types.SignInOutput
import com.clarxlabs.ellion.core.repositories.types.SignOutError
import com.clarxlabs.ellion.core.repositories.types.SignOutInput
import com.clarxlabs.ellion.core.repositories.types.SignOutOutput
import com.clarxlabs.ellion.core.repositories.types.SignUpError
import com.clarxlabs.ellion.core.repositories.types.SignUpInput
import com.clarxlabs.ellion.core.repositories.types.SignUpOutput
import com.clarxlabs.ellion.core.repositories.types.UserInfoError
import com.clarxlabs.ellion.core.repositories.types.UserInfoInput
import com.clarxlabs.ellion.core.repositories.types.UserInfoOutput
import com.clarxlabs.ellion.core.repositories.types.VerifyError
import com.clarxlabs.ellion.core.repositories.types.VerifyInput
import com.clarxlabs.ellion.core.repositories.types.VerifyOutput
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import it.czerwinski.kotlin.util.Either

class AuthenticationDataSourceImpl(private val httpClient: HttpClient) : AuthenticationDataSource {
    override suspend fun signIn(input: SignInInput): Either<SignInError, SignInOutput> {
        return HttpRequestFactory(httpClient)
            .create(HttpMethod.Post, AppAPI.SIGNIN).setBody(input)
            .execute().let { APIResponse.toEither(it) }
    }

    override suspend fun signUp(input: SignUpInput): Either<SignUpError, SignUpOutput> =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Post, AppAPI.SIGNUP).setBody(input)
            .execute().let { APIResponse.toEither(it) }

    override suspend fun signOut(input: SignOutInput): Either<SignOutError, SignOutOutput> {
        val queries = mapOf(
            "access_token" to input.accessToken,
            "refresh_token" to input.refreshToken,
        )

        return HttpRequestFactory(httpClient)
            .create(HttpMethod.Delete, AppAPI.SIGNOUT).setQueries(queries)
            .execute().let { APIResponse.toEither(it) }
    }

    override suspend fun verify(input: VerifyInput): Either<VerifyError, VerifyOutput> {
        val queries = mapOf("email".to(input.email))

        return HttpRequestFactory(httpClient)
            .create(HttpMethod.Get, AppAPI.VERIFY).setQueries(queries)
            .execute().let { APIResponse.toEither(it) }
    }

    override suspend fun confirm(input: ConfirmInput): Either<ConfirmError, ConfirmOutput> {
        val queries = mapOf("email".to(input.email), "code".to(input.code))

        return HttpRequestFactory(httpClient)
            .create(HttpMethod.Post, AppAPI.CONFIRM).setQueries(queries)
            .execute().let { APIResponse.toEither(it) }
    }

    override suspend fun userInfo(input: UserInfoInput): Either<UserInfoError, UserInfoOutput> {
        val headers = mapOf("authorization".to("Bearer ${input.accessToken}"))

        return HttpRequestFactory(httpClient)
            .create(HttpMethod.Get, AppAPI.USERINFO).setHeaders(headers)
            .execute().let { APIResponse.toEither(it) }
    }

    override suspend fun listUsers(): Either<ListUsersError, ListUsersOutput> =
        HttpRequestFactory(httpClient)
            .create(HttpMethod.Get, AppAPI.LIST_USERS)
            .execute().let { APIResponse.toEither(it) }
}
