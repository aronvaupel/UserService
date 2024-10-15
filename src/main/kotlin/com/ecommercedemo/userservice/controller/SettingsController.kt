package com.ecommercedemo.userservice.controller

import com.ecommercedemo.common.model.CustomProperty
import com.ecommercedemo.userservice.model.contactdata.ContactData
import com.ecommercedemo.userservice.model.customProperty.UserServiceCustomProperty
import com.ecommercedemo.userservice.model.user.User
import com.ecommercedemo.userservice.service.customproperty.CustomPropertyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/settings")
class SettingsController(
    private val customPropertyService: CustomPropertyService,
) {

    @PostMapping("/custom-property/user")
    fun createUserCustomProperty(
        @RequestParam userId: UUID,
        @RequestBody customProperty: UserServiceCustomProperty
    ): ResponseEntity<CustomProperty> {
        val createdProperty = customPropertyService.addCustomPropertyToAllUsers(customProperty)
        return ResponseEntity.ok(createdProperty)
    }

    @PostMapping("/custom-property/contact-data")
    fun createContactDataCustomProperty(
        @RequestParam contactDataId: UUID,
        @RequestBody customProperty: UserServiceCustomProperty
    ): ResponseEntity<CustomProperty> {
        val createdProperty = customPropertyService.addCustomPropertyToAllContactData(customProperty)
        return ResponseEntity.ok(createdProperty)
    }

    @PutMapping("/custom-property/user/{key}")
    fun renameUserCustomProperty(
        @PathVariable key: String,
        @RequestParam newKey: String
    ): ResponseEntity<UserServiceCustomProperty> {
        return ResponseEntity.ok(customPropertyService.renameCustomProperty("ContactData", key, newKey))
    }

    @PutMapping("/custom-property/contact-data/key/{key}")
    fun renameContactDataCustomProperty(
        @PathVariable key: String,
        @RequestParam newKey: String
    ): ResponseEntity<UserServiceCustomProperty> {
        return ResponseEntity.ok(customPropertyService.renameCustomProperty("ContactData", key, newKey))
    }

    @DeleteMapping("/custom-property/user/{key}")
    fun deleteUserCustomProperty(
        @PathVariable key: String
    ): ResponseEntity<Void> {
        customPropertyService.deleteCustomPropertyByEntityAndKey(User::class.simpleName!!, key)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/custom-property/contact-data/{key}")
    fun deleteContactDataCustomProperty(
        @PathVariable key: String
    ): ResponseEntity<Void> {
        customPropertyService.deleteCustomPropertyByEntityAndKey(ContactData::class.simpleName!!, key)
        return ResponseEntity.noContent().build()
    }
}