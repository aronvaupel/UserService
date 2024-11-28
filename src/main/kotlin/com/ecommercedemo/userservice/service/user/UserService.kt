package com.ecommercedemo.userservice.service.user

import com.ecommercedemo.common.application.event.EntityEventProducer
import com.ecommercedemo.common.controller.abstraction.util.Retriever
import com.ecommercedemo.common.persistence.abstraction.EntityRepository
import com.ecommercedemo.common.persistence.abstraction.IEntityPersistenceAdapter
import com.ecommercedemo.common.service.abstraction.ServiceTemplate
import com.ecommercedemo.userservice.model._pseudoProperty._PseudoProperty
import com.ecommercedemo.userservice.model.user.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Suppress("")
@Service
class UserService(
    adapter: IEntityPersistenceAdapter<User>,
    eventProducer: EntityEventProducer,
    objectMapper: ObjectMapper,
    _pseudoPropertyRepository: EntityRepository<_PseudoProperty, UUID>,
    retriever: Retriever,
) : ServiceTemplate<User>(
    adapter,
    User::class,
    eventProducer,
    objectMapper,
    _pseudoPropertyRepository,
    retriever,
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
