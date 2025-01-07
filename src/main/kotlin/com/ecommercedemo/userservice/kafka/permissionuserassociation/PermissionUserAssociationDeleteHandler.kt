package com.ecommercedemo.userservice.kafka.permissionuserassociation

import com.ecommercedemo.common.application.kafka.EntityEvent
import com.ecommercedemo.common.application.kafka.EntityEventProducer
import com.ecommercedemo.common.application.kafka.handling.abstraction.IDeleteHandler
import com.ecommercedemo.common.model.concretion.permissionuserassociation.PermissionUserAssociation
import com.ecommercedemo.common.service.concretion.permissionuserassociation.PermissionUserAssociationEventService
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.persistence.user.UserPersistenceAdapter
import org.springframework.stereotype.Service
import java.util.*

@Service
class PermissionUserAssociationDeleteHandler(
    private val permissionUserAssociationEventService: PermissionUserAssociationEventService,
    private val userAdapter: UserPersistenceAdapter,
    private val entityEventProducer: EntityEventProducer,
) : IDeleteHandler<PermissionUserAssociation> {
    override fun applyChanges(event: EntityEvent) {
        permissionUserAssociationEventService.deleteByEvent(event)
        val user = userAdapter.getById(event.id)
        val permissionId =
            event.properties[PermissionUserAssociation::permissionId.name] as? UUID
                ?: throw IllegalArgumentException("PermissionId is required")
        user.permissions.toMutableList().remove(permissionId)
        userAdapter.save(user)
        entityEventProducer.emit(
            User::class.java.simpleName,
            user.id,
            event.type,
            mutableMapOf(User::permissions.name to user.permissions)
        )
    }

}
