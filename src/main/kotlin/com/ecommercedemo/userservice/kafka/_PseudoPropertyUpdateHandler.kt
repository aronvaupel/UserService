package com.ecommercedemo.userservice.kafka


import com.ecommercedemo.common.application.kafka.EntityEvent
import com.ecommercedemo.common.application.kafka.EntityEventType
import com.ecommercedemo.common.application.kafka.handling.abstraction.IUpdateHandler
import com.ecommercedemo.common.model.concretion._pseudoProperty._PseudoProperty
import com.ecommercedemo.common.service.concretion._pseudoProperty._PseudoPropertyApplier
import com.ecommercedemo.common.service.concretion._pseudoProperty._PseudoPropertyEventService
import com.ecommercedemo.common.service.concretion._pseudoProperty._PseudoPropertyRestService
import com.ecommercedemo.userservice.model.user.User
import org.springframework.stereotype.Service

@Suppress("ClassName")
@Service
class _PseudoPropertyUpdateHandler(
    private val pseudoPropertyApplier: _PseudoPropertyApplier,
    private val _pseudoPropertyEventService: _PseudoPropertyEventService,
    private val _pseudoPropertyService: _PseudoPropertyRestService,
) : IUpdateHandler<_PseudoProperty> {
    override fun applyChanges(event: EntityEvent) {
        val before = _pseudoPropertyService.getSingle(event.id)
        _pseudoPropertyEventService.updateByEvent(event)
        if (
            (event.type == EntityEventType.CREATE
            && event.properties[_PseudoProperty::entitySimpleName.name] == User::class.simpleName)
            || before.entitySimpleName == User::class.simpleName)
            pseudoPropertyApplier.renamePseudoPropertyForAllEntitiesOfType(
                User::class.java,
                before.key,
                event.properties[_PseudoProperty::key.name].toString()
            )
    }
}