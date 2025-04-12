package com.clarxlabs.ellion.core.repositories.types

import com.clarxlabs.ellion.core.dtos.UserTokens
import com.clarxlabs.ellion.core.entities.User

typealias UserInfoInput = UserTokens
typealias UserInfoError = UserTokens.Error
typealias UserInfoOutput = User
