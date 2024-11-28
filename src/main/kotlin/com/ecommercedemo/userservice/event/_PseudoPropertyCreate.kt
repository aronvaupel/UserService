package com.ecommercedemo.userservice.event

import com.ecommercedemo.common.application.event.EntityEvent
import com.ecommercedemo.common.application.event.ICreateTypeUseCase
import com.ecommercedemo.common.model.abstraction.ExpandableBaseEntity
import com.ecommercedemo.common.service.concretion.PseudoPropertyApplier
import com.ecommercedemo.userservice.model._pseudoProperty._PseudoProperty
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.service._pseudoproperty._PseudoPropertyService
import org.springframework.stereotype.Service

@Suppress("ClassName")
@Service
class _PseudoPropertyCreate(
    private val pseudoPropertyApplier: PseudoPropertyApplier,
    private val _pseudoPropertyService: _PseudoPropertyService
): ICreateTypeUseCase<_PseudoProperty> {
    override fun applyChanges(event: EntityEvent<_PseudoProperty>) {
       _pseudoPropertyService.createByEvent(event)
        if (event.properties["entitySimpleName"] == User::class.simpleName)
       pseudoPropertyApplier.addPseudoPropertyToAllEntitiesOfType(User::class.java as Class<out ExpandableBaseEntity>, event.properties["key"].toString())
    }
}