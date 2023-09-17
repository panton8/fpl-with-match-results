package com.panton.domain

final case class Club(
  id: Id,
  name: Name,
  founded: Date,
  stadium: Name,
  manager: Manager,
)
