package com.ecommercedemo.userservice.service.user

import com.ecommercedemo.common.service.abstraction.RestServiceTemplate
import com.ecommercedemo.common.service.annotation.RestServiceFor
import com.ecommercedemo.userservice.model.user.User
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
@RestServiceFor(User::class)
class UserRestService: RestServiceTemplate<User>() {
    fun importUsersFromExcel(file: MultipartFile, sheet: String, rows: Int, evaluationColumn: String) {
                TODO("Not yet implemented")
    }

    fun exportUsersToExcel(filter: String?, userInfoProperties: List<String>?): ByteArray? {
                TODO("Not yet implemented")
    }

    fun exportUsersToPdf(filter: String?): ByteArray? {
                TODO("Not yet implemented")
    }
}
