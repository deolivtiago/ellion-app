package com.clarxlabs.ellion.auth.data.remote

import com.clarxlabs.ellion.auth.data.remote.inputs.*
import io.ktor.client.statement.HttpResponse

interface AuthDataSource {
    suspend fun signUp(input: SignUpInput): HttpResponse
    suspend fun signIn(input: SignInInput): HttpResponse
    suspend fun signOut(input: SignOutInput): HttpResponse
    suspend fun verify(input: VerifyInput): HttpResponse
    suspend fun confirm(input: ConfirmInput): HttpResponse
}
