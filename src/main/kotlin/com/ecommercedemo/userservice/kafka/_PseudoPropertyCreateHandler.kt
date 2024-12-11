package com.ecommercedemo.userservice.kafka

import com.ecommercedemo.common.application.kafka.EntityEvent
import com.ecommercedemo.common.application.kafka.handling.abstraction.ICreateHandler
import com.ecommercedemo.common.model.abstraction.ExpandableBaseEntity
import com.ecommercedemo.common.model.concretion._pseudoProperty._PseudoProperty
import com.ecommercedemo.common.service.concretion._pseudoProperty._PseudoPropertyApplier
import com.ecommercedemo.common.service.concretion._pseudoProperty._PseudoPropertyEventService
import com.ecommercedemo.userservice.model.user.User
import org.springframework.stereotype.Service

@Suppress("ClassName")
@Service
class _PseudoPropertyCreateHandler(
    private val pseudoPropertyApplier: _PseudoPropertyApplier,
    private val _pseudoPropertyEventService: _PseudoPropertyEventService
) : ICreateHandler<_PseudoProperty> {
    override fun applyChanges(event: EntityEvent) {
        _pseudoPropertyEventService.createByEvent(event)
        if (event.properties[_PseudoProperty::entitySimpleName.name] == User::class.simpleName)
            pseudoPropertyApplier.addPseudoPropertyToAllEntitiesOfType(
                User::class.java as Class<out ExpandableBaseEntity>,
                event.properties["key"].toString()
            )
    }
}