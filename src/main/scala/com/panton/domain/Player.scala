package com.panton.domain
import com.panton.domain.Status.Healthy

final case class Player(
  id: Id,
  name: Name,
  surname: Name,
  price: Price,
  position: Position,
  status: Status = Healthy,
  club: Club
)
