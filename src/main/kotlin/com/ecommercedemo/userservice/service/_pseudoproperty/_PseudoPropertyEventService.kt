package com.ecommercedemo.userservice.service._pseudoproperty

import com.ecommercedemo.common.model.concretion.PseudoProperty
import com.ecommercedemo.common.persistence.abstraction.IEntityPersistenceAdapter
import com.ecommercedemo.common.service.abstraction.EventServiceTemplate
import com.ecommercedemo.common.service.concretion.ServiceUtility
import com.ecommercedemo.userservice.model._pseudoProperty._PseudoProperty
import org.springframework.stereotype.Service

@Suppress("ClassName")
@Service
class _PseudoPropertyEventService(
    adapter: IEntityPersistenceAdapter<_PseudoProperty>,
    serviceUtility: ServiceUtility
): EventServiceTemplate<PseudoProperty, _PseudoProperty>(
    adapter,
    serviceUtility,
    _PseudoProperty::class
)