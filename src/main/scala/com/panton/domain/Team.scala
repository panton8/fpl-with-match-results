package com.panton.domain

final case class Team(
  id: Id,
  name: Name,
  points: Point = Point(0),
  freeTransfers: Transfer = Transfer(2)
)
