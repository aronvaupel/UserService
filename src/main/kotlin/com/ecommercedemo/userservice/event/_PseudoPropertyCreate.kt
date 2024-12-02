package com.ecommercedemo.userservice.event

import com.ecommercedemo.common.application.event.EntityEvent
import com.ecommercedemo.common.application.event.ICreateTypeUseCase
import com.ecommercedemo.common.model.abstraction.ExpandableBaseEntity
import com.ecommercedemo.common.model.concretion.PseudoProperty
import com.ecommercedemo.common.service.concretion.PseudoPropertyApplier
import com.ecommercedemo.userservice.model._pseudoProperty._PseudoProperty
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.service._pseudoproperty._PseudoPropertyEventService
import org.springframework.stereotype.Service

@Suppress("ClassName")
@Service
class _PseudoPropertyCreate(
    private val pseudoPropertyApplier: PseudoPropertyApplier,
    private val _pseudoPropertyEventService: _PseudoPropertyEventService
): ICreateTypeUseCase<PseudoProperty> {
    override fun applyChanges(event: EntityEvent<PseudoProperty>) {
       _pseudoPropertyEventService.createByEvent(event)
        if (event.properties[_PseudoProperty::entitySimpleName.name] == User::class.simpleName)
       pseudoPropertyApplier.addPseudoPropertyToAllEntitiesOfType(User::class.java as Class<out ExpandableBaseEntity>, event.properties["key"].toString())
    }
}