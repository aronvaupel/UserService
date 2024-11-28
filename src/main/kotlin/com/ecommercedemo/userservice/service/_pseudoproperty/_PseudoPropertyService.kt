package com.ecommercedemo.userservice.service._pseudoproperty

import com.ecommercedemo.common.application.event.EntityEventProducer
import com.ecommercedemo.common.controller.abstraction.util.Retriever
import com.ecommercedemo.common.persistence.abstraction.IEntityPersistenceAdapter
import com.ecommercedemo.common.persistence.concretion.PseudoPropertyRepository
import com.ecommercedemo.common.service.abstraction.ServiceTemplate
import com.ecommercedemo.userservice.model._pseudoProperty._PseudoProperty
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Suppress("ClassName")
@Transactional
@Service
class _PseudoPropertyService(
    adapter: IEntityPersistenceAdapter<_PseudoProperty>,
    eventProducer: EntityEventProducer,
    objectMapper: ObjectMapper,
    pseudoPropertyRepository: PseudoPropertyRepository,
    retriever: Retriever,
) : ServiceTemplate<_PseudoProperty>(
    adapter,
    _PseudoProperty::class,
    eventProducer,
    objectMapper,
    pseudoPropertyRepository,
    retriever,
    )