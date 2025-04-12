package com.clarxlabs.ellion.core.repositories.types

import com.clarxlabs.ellion.core.dtos.UserData
import com.clarxlabs.ellion.core.entities.User

typealias SignUpInput = UserData
typealias SignUpError = UserData.Error
typealias SignUpOutput = User
