package com.clarxlabs.ellion.auth.data.remote

import com.clarxlabs.ellion.application.utilities.Result
import com.clarxlabs.ellion.auth.data.remote.dtos.CredentialsData
import com.clarxlabs.ellion.auth.data.remote.dtos.CredentialsError
import com.clarxlabs.ellion.auth.data.remote.dtos.TokensData
import com.clarxlabs.ellion.auth.data.remote.dtos.UserData
import com.clarxlabs.ellion.auth.data.remote.dtos.UserDataError
import com.clarxlabs.ellion.auth.data.remote.inputs.*
import com.clarxlabs.ellion.auth.domain.entities.User
import io.ktor.client.statement.HttpResponse

interface AuthDataSource {
    suspend fun signIn(input: CredentialsData): Result<TokensData, CredentialsError>
    suspend fun signUp(input: UserData): Result<User, UserDataError>
    suspend fun signOut(input: SignOutInput): HttpResponse
    suspend fun verify(input: VerifyInput): HttpResponse
    suspend fun confirm(input: ConfirmInput): HttpResponse
    suspend fun listUsers(): HttpResponse
}
