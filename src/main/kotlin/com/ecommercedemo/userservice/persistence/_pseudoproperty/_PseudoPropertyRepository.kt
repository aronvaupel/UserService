package com.ecommercedemo.userservice.persistence._pseudoproperty

import com.ecommercedemo.common.persistence.abstraction.IPseudoPropertyRepository
import com.ecommercedemo.userservice.model._pseudoProperty._PseudoProperty

@Suppress("ClassName")
interface _PseudoPropertyRepository : IPseudoPropertyRepository<_PseudoProperty> {
    override fun findAllByEntitySimpleName(entitySimpleName: String): List<_PseudoProperty>
}

