package com.ecommercedemo.userservice.persistence.user

import com.ecommercedemo.common.persistence.abstraction.IEntityPersistenceAdapter
import com.ecommercedemo.userservice.model.user.User

interface IUserPersistenceAdapter : IEntityPersistenceAdapter<User>