package com.panton.domain

import java.sql.Date

final case class Club(
  id: Id,
  name: Name,
  founded: Date,
  stadium: Name,
  manager: Manager,
)
