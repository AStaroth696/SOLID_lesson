package com.fractaldev.solidexample.utils

import java.util.UUID
import javax.inject.Inject

class UserIdGenerator @Inject constructor() {

    fun generateUserId(): String {
        return UUID.randomUUID().toString()
    }

}