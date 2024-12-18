package com.ecommercedemo.userservice.persistence.user

import com.ecommercedemo.common.persistence.PersistenceAdapterFor
import com.ecommercedemo.common.persistence.abstraction.EntityPersistenceAdapter
import com.ecommercedemo.userservice.model.user.User
import org.springframework.stereotype.Service

@Service
@PersistenceAdapterFor(User::class)
class UserPersistenceAdapter: EntityPersistenceAdapter<User>()

