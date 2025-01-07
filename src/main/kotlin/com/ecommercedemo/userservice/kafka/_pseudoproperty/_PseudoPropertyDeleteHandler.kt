package com.ecommercedemo.userservice.kafka._pseudoproperty

import com.ecommercedemo.common.application.kafka.EntityEvent
import com.ecommercedemo.common.application.kafka.handling.abstraction.IDeleteHandler
import com.ecommercedemo.common.model.concretion._pseudoProperty._PseudoProperty
import com.ecommercedemo.common.service.concretion._pseudoProperty._PseudoPropertyApplier
import com.ecommercedemo.common.service.concretion._pseudoProperty._PseudoPropertyEventService
import com.ecommercedemo.common.service.concretion._pseudoProperty._PseudoPropertyRestService
import com.ecommercedemo.userservice.model.user.User
import org.springframework.stereotype.Service

@Suppress("ClassName")
@Service
class _PseudoPropertyDeleteHandler(
    private val pseudoPropertyApplier: _PseudoPropertyApplier,
    private val _pseudoPropertyEventService: _PseudoPropertyEventService,
    private val _pseudoPropertyService: _PseudoPropertyRestService
) : IDeleteHandler<_PseudoProperty> {
    override fun applyChanges(event: EntityEvent) {
        val before = _pseudoPropertyService.getSingle(event.id)
        pseudoPropertyApplier.deletePseudoPropertyForAllEntitiesOfType(
            User::class.java,
            before.key
        )
        _pseudoPropertyEventService.deleteByEvent(event)
    }
}