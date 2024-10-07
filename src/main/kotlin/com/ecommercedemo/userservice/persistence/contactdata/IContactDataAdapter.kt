package com.ecommercedemo.userservice.persistence.contactdata

import com.ecommercedemo.userservice.model.contactdata.ContactData

interface IContactDataAdapter {
    fun saveContactData(data: ContactData)
    fun getContactDataById()
    fun updateContactData()
    fun deleteContactData()
    fun existsByMail(email: String): Boolean
}