package com.panton.domain

final case class User(
  id: Id,
  username: Name,
  email: Email,
  password: Password,
  access: Access,
  budget: Budget
)
