package com.ecommercedemo.userservice.service._pseudoproperty

import com.ecommercedemo.common.application.event.EntityEventProducer
import com.ecommercedemo.common.controller.abstraction.util.Retriever
import com.ecommercedemo.common.persistence.abstraction.IEntityPersistenceAdapter
import com.ecommercedemo.common.service.abstraction.RestServiceTemplate
import com.ecommercedemo.common.service.concretion.ServiceUtility
import com.ecommercedemo.userservice.model._pseudoProperty._PseudoProperty
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Suppress("ClassName")
@Transactional
@Service
class _PseudoPropertyRestService(
    adapter: IEntityPersistenceAdapter<_PseudoProperty>,
    entityManager: EntityManager,
    eventProducer: EntityEventProducer,
    retriever: Retriever,
    serviceUtility: ServiceUtility
) : RestServiceTemplate<_PseudoProperty>(
    adapter,
    _PseudoProperty::class,
    entityManager,
    eventProducer,
    retriever,
    serviceUtility
    )