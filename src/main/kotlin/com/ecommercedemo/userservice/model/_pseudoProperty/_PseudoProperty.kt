package com.ecommercedemo.userservice.model._pseudoProperty

import com.ecommercedemo.common.application.validation.type.ValueType
import com.ecommercedemo.common.controller.abstraction.util.TypeDescriptor
import com.ecommercedemo.common.model.abstraction.BasePseudoProperty
import com.fasterxml.jackson.databind.ObjectMapper
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.annotations.Type
import java.util.*

@Suppress("unused", "ClassName")
@Entity
@Table(name = _PseudoProperty.STORAGE_NAME)
open class _PseudoProperty(
    override val id: UUID = UUID.randomUUID(),
    @NotNull
    @NotBlank
    @Column(updatable = false)
    override val entitySimpleName: String,
    @NotNull
    @NotBlank
    override var key: String = "",
    @Type(JsonBinaryType::class)
    @Column(columnDefinition = "jsonb", updatable = false)
    override var typeDescriptor: String
) : BasePseudoProperty() {
    constructor() : this(
        UUID.randomUUID(),
        "DUMMY_CLASS",
        "DUMMY_KEY",
        ObjectMapper().writeValueAsString(TypeDescriptor.PrimitiveDescriptor(ValueType.STRING))
    )

    companion object {
        const val STORAGE_NAME = "pseudo_properties"
    }

    override fun copy(): _PseudoProperty {
        return _PseudoProperty(
            id = this.id,
            entitySimpleName = this.entitySimpleName,
            key = this.key,
            typeDescriptor = this.typeDescriptor
        ).apply {
            this.createdAt = this@_PseudoProperty.createdAt
            this.updatedAt = this@_PseudoProperty.updatedAt
        }
    }
}