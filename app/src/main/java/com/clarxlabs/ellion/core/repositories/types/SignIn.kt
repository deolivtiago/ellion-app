package com.clarxlabs.ellion.core.repositories.types

import com.clarxlabs.ellion.core.dtos.UserCredentials
import com.clarxlabs.ellion.core.dtos.UserTokens

typealias SignInInput = UserCredentials
typealias SignInError = UserCredentials.Error
typealias SignInOutput = UserTokens
