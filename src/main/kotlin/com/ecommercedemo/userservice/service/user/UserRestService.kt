package com.ecommercedemo.userservice.service.user

import com.ecommercedemo.common.application.event.EntityEventProducer
import com.ecommercedemo.common.controller.abstraction.util.Retriever
import com.ecommercedemo.common.persistence.abstraction.IEntityPersistenceAdapter
import com.ecommercedemo.common.service.abstraction.RestServiceTemplate
import com.ecommercedemo.common.service.concretion.ServiceUtility
import com.ecommercedemo.userservice.model.user.User
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Suppress("")
@Service
class UserRestService(
    adapter: IEntityPersistenceAdapter<User>,
    entityManager: EntityManager,
    eventProducer: EntityEventProducer,
    retriever: Retriever,
    serviceUtility: ServiceUtility,
) : RestServiceTemplate<User>(
    adapter,
    User::class,
    entityManager,
    eventProducer,
    retriever,
    serviceUtility
) {
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
