package com.ecommercedemo.userservice.persistence._pseudoproperty

import com.ecommercedemo.common.persistence.abstraction.EntityRepository
import com.ecommercedemo.common.persistence.abstraction.PostgresEntityPersistenceAdapter
import com.ecommercedemo.userservice.model._pseudoProperty._PseudoProperty
import org.springframework.stereotype.Service
import java.util.*

@Suppress("ClassName")
@Service
open class Postgres_PseudoPropertyPersistenceAdapter(
    repository: EntityRepository<_PseudoProperty, UUID>,
) : PostgresEntityPersistenceAdapter<_PseudoProperty>(repository)