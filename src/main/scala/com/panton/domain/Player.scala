package com.panton.domain

final case class Player(
  id: Id,
  name: Name,
  surname: Name,
  price: Price,
  position: Position,
  isHealthy: Boolean = true,
  club: Club
)
